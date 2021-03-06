package link;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import domain.enums.DataType;
import domain.enums.ReferenceType;
import error.ApiNotReachableException;
import error.ErrorMessages;
import error.InvalidTokenException;
import error.KoronaCloudAPIErrorMessageException;
import link.thread.PostListThread;



/**
 * This Class is our Interface to the Cloud
 *
 * @author Gordon Bosch
 */
public class ApiConnector
{
	private final static Logger LOGGER = Logger.getLogger(ApiConnector.class);

	private final String cloudURL;

	private final String token;

	/**
	 * trustAllCerts is accepting all Certs in Case we are handling a
	 * Https-Connection
	 */
	private final static TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
		@Override
		public void checkClientTrusted(final X509Certificate[] certs, final String authType)
		{
		}


		@Override
		public void checkServerTrusted(final X509Certificate[] certs, final String authType)
		{
		}


		@Override
		public X509Certificate[] getAcceptedIssuers()
		{
			return null;
		}
	} };


	public ApiConnector(final String cloudURL, final String token)
	{
		super();
		this.cloudURL = cloudURL;
		this.token = token;
	}


	private URL convertToUrl(final String string)
	{
		try
		{
			String decodedURL = URLDecoder.decode(string, "UTF-8");
			URL url = new URL(decodedURL);
			URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
			return uri.toURL();
		}
		catch (UnsupportedEncodingException | URISyntaxException | MalformedURLException e)
		{
			LOGGER.error("error encoding url: ", e);
			return null;
		}
	}


	/**
	 * Gets an Object from the Cloud
	 *
	 * @param type
	 * @param reference
	 * @return
	 * @throws InvalidTokenException
	 * @throws KoronaCloudAPIErrorMessageException
	 * @throws JSONException
	 * @throws IOException
	 */
	public String fetchData(final DataType type, final domain.enums.ReferenceType refType, final String reference) throws ApiNotReachableException, KoronaCloudAPIErrorMessageException, InvalidTokenException
	{
		String url;
		String slash = "";

		if (!cloudURL.endsWith("/"))
		{
			slash = "/";
		}

		if (refType.equals(ReferenceType.auth))
		{
			url = cloudURL + slash + refType.getType() + "/" + reference;
		}
		else
		{
			url = cloudURL + slash + token + "/" + type.getReference() + "/" + refType.getType() + "/" + reference;
		}

		HttpURLConnection con = null;

		URL obj = null;

		try
		{
			obj = convertToUrl(url);
			if (cloudURL.contains("https"))
			{
				setupConnection();
				con = (HttpsURLConnection) obj.openConnection();
			}
			else
			{
				con = (HttpURLConnection) obj.openConnection();
			}
			con.setRequestMethod("GET");
			con.setDoOutput(true);
			con.setConnectTimeout(5000000);
			con.setReadTimeout(5000000);
			con.setRequestProperty("Content-Type", "application/json");
			con.connect();
			final BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			String inputLine;
			final StringBuilder response = new StringBuilder();
			while ((inputLine = in.readLine()) != null)
			{
				response.append(inputLine);
			}
			in.close();
			if (con.getResponseCode() == 200)
			{
				LOGGER.debug("APICON:GET -> Type:" + type.getReference() + " JSON=" + obj.toString());

				try
				{
					interpretResponse(new JSONObject(response.toString()));
				}
				catch (final JSONException e)
				{
				}

				return response.toString();
			}
			else
			{
				LOGGER.debug("ERR: APICON:GET -> Type:" + type.getReference() + " JSON=" + obj.toString());
				LOGGER.error(con.getResponseMessage() + ":" + con.getResponseCode());

				return null;
			}
		}
		catch (final IOException e)
		{
			LOGGER.error(type.toString(), e);
			throw new ApiNotReachableException(obj.toString(), e);
		}
		finally
		{
			if (con != null)
			{
				con.disconnect();
			}
		}
	}


	/**
	 * saves a JSONArray in the Cloud
	 *
	 * @param type
	 * @param obj
	 * @return
	 * @throws KoronaCloudAPIErrorMessageException
	 * @throws InvalidTokenException
	 * @throws ApiNotReachableException
	 */
	public JSONArray postData(final DataType type, final JSONArray obj, final int limit, final int threads) throws KoronaCloudAPIErrorMessageException, InvalidTokenException, ApiNotReachableException
	{

		final ExecutorService exec = Executors.newFixedThreadPool(threads);

		final Set<Future<String>> set = new HashSet<Future<String>>();

		int offset = 0;

		int i = 0;

		if (obj.length() == 0)
		{
			LOGGER.info("NO OBJECTS POSTED");
		}
		else
		{
			LOGGER.info("posting " + obj.length() + " " + type + "s");
		}

		while (i < obj.length())
		{

			final JSONArray threadedJson = new JSONArray();

			offset = offset + limit;

			while (i < offset && i < obj.length())
			{
				try
				{

					threadedJson.put(obj.get(i));

				}
				catch (final JSONException e)
				{

					LOGGER.error("could not split JSONArray into threads", e);

				}

				i++;
			}

			final Callable<String> callable = new PostListThread(cloudURL, token, type, threadedJson);

			final Future<String> future = exec.submit(callable);

			set.add(future);

		}
		JSONArray ret = null;
		for (final Future<String> future : set)
		{
			JSONObject jsonObject;
			try
			{

				final String jsonStr = future.get();

				jsonObject = new JSONObject(jsonStr);

				interpretResponse(jsonObject);

				if (jsonObject.has("resultList") && !jsonObject.isNull("resultList"))
				{
					if (ret == null)
					{
						ret = new JSONArray();
					}

					ret.put(jsonObject.getJSONArray("resultList"));

				}

			}
			catch (final JSONException e)
			{
				LOGGER.error("Could not interpret KORONA.CLOUD.API response message", e);
			}
			catch (final CancellationException e)
			{
				LOGGER.error(e);
			}
			catch (final InterruptedException e)
			{
				LOGGER.error(e);
			}
			catch (final ExecutionException e)
			{
				if (e.getCause() instanceof ApiNotReachableException)
					throw new ApiNotReachableException(cloudURL, e.getCause());
			}

			finally
			{
				exec.shutdown();
			}
		}
		return ret;

	}


	/**
	 * saves a JSONObject in the Cloud
	 *
	 * @param type
	 * @param obj
	 * @return
	 * @throws InvalidTokenException
	 * @throws KoronaCloudAPIErrorMessageException
	 */
	public String postData(final DataType type, final JSONObject obj) throws KoronaCloudAPIErrorMessageException, InvalidTokenException
	{

		String slash = "";
		if (!cloudURL.endsWith("/"))
		{
			slash = "/";
		}
		final String url = cloudURL + slash + token + "/" + type.getReference() + "/save/";
		try
		{
			final URL posturl = convertToUrl(url);
			HttpURLConnection con;
			if (cloudURL.contains("https"))
			{
				setupConnection();
				con = (HttpsURLConnection) posturl.openConnection();
			}
			else
			{
				con = (HttpURLConnection) posturl.openConnection();
			}
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			con.setConnectTimeout(3000000);
			con.setReadTimeout(3000000);
			con.setRequestProperty("Content-Type", "application/json");
			con.connect();

			final OutputStream out = con.getOutputStream();
			final OutputStreamWriter wr = new OutputStreamWriter(out, "UTF-8");
			wr.write(obj.toString());
			wr.flush();
			wr.close();
			if (out != null)
			{
				out.close();
			}
			if (con.getResponseCode() == 200)
			{
				final BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
				String inputLine;
				final StringBuilder response = new StringBuilder();
				while ((inputLine = in.readLine()) != null)
				{
					response.append(inputLine);
				}
				in.close();
				LOGGER.info("APICON:POST -> Type:" + type.getReference() + " JSON=" + obj.toString());
				con.disconnect(); // Disconnect

				try
				{
					final JSONObject responseJson = new JSONObject(response.toString());
					interpretResponse(responseJson);
				}
				catch (final JSONException e)
				{
					LOGGER.error("Could not interpret responseMessage from API! Malformed JSONSyntax");
				}

				return response.toString();
			}
			else
			{
				final BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
				String inputLine;
				final StringBuilder response = new StringBuilder();
				while ((inputLine = in.readLine()) != null)
				{
					response.append(inputLine);
				}
				in.close();

				LOGGER.error("APICON:FAILED POST -> Type:" + type.getReference() + " JSON=" + obj.toString());
				con.disconnect(); // Disconnect

				try
				{

					final JSONObject responseJson = new JSONObject(response.toString());

					interpretResponse(responseJson);

				}
				catch (final JSONException e)
				{
					LOGGER.error("Could not interpret responseMessage from API! Malformed JSONSyntax");
				}

				LOGGER.error(con.getResponseMessage() + ":" + con.getResponseCode());
				return response.toString();
			}
		}
		catch (final IOException e)
		{
			LOGGER.error(e);

			return null;
		}
	}


	/**
	 * @param responseJson
	 * @throws JSONException
	 * @throws KoronaCloudAPIErrorMessageException
	 * @throws InvalidTokenException
	 * @throws ArticleCodeMustBeUniqueException
	 */
	private void interpretResponse(final JSONObject responseJson) throws JSONException, KoronaCloudAPIErrorMessageException, InvalidTokenException
	{

		try
		{
			// Invalid Token
			if (!responseJson.isNull("error"))
			{

				final String errorStr = responseJson.getString("error");

				if (errorStr.equalsIgnoreCase(ErrorMessages.Invalid_Token.getErrorString()))
					throw new InvalidTokenException(null);

				else
				{

					final Map<String, String> errorMap = new HashMap<String, String>();

					final String[] errorMapping = errorStr.split(":");

					if (errorMapping.length == 1)
					{
						errorMap.put(errorMapping[0], "no further information");
					}
					else
					{
						errorMap.put(errorMapping[0], errorMapping[1]);
					}

					throw new KoronaCloudAPIErrorMessageException(null, errorMap);

				}

			}

			// API Exception Handling
			if (!responseJson.isNull("errorList"))
			{
				final JSONArray errorList = responseJson.getJSONArray("errorList");

				// contains all error objects
				final Map<String, String> errorMap = new HashMap<String, String>();

				for (int i = 0; i < errorList.length(); i++)
				{

					if (errorList.getString(i).equalsIgnoreCase(ErrorMessages.Invalid_Token.getErrorString()))
						throw new InvalidTokenException(null);

					final String[] errorMapping = errorList.getString(i).split(":");

					if (errorMap.containsKey(errorMapping[0]))
					{

						final String errorValue = errorMap.get(errorMapping[0]);

						errorMap.put(errorMapping[0], errorValue + ", " + errorMapping[1]);

					}
					else
					{
						errorMap.put(errorMapping[0], errorMapping[1]);
					}

					if (errorMap.containsKey("Invalid Token"))
						throw new InvalidTokenException(null);

				}

				throw new KoronaCloudAPIErrorMessageException(null, errorMap);

			}
		}
		catch (final JSONException e)
		{
			LOGGER.error("Missing error or errorList key: " + responseJson.toString(), e);
		}

	}


	/**
	 * applies our low-security Profile to our Connection
	 */
	private void setupConnection()
	{
		try
		{
			final SSLContext sc = SSLContext.getInstance("TLS");
			sc.init(null, trustAllCerts, new SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		}
		catch (final Exception e)
		{
			LOGGER.error(e);
		}
	}
}
