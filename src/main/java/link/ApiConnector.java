package link;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import domain.DataType;
import error.ApiNotReachableException;

/**
 * This Class is our Interface to the Cloud
 * 
 * @author Gordon Bosch
 * 
 */
public class ApiConnector
{
	private final SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss");
	private final String cloudURL;
	private final String token;

	/**
	 * trustAllCerts is accepting all Certs in Case we are handling a Https-Connection
	 */
	private final static TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager()
	{
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

	/**
	 * Gets an Object from the Cloud
	 * 
	 * @param type
	 * @param reference
	 * @return
	 * @throws IOException
	 */
	public String fetchData(final DataType type, final domain.ReferenceType refType,
		final String reference) throws ApiNotReachableException
	{

		String url;
		String slash = "";

		if (!cloudURL.endsWith("/"))
			slash = "/";

		url = cloudURL + slash + token + "/" + type.getReference() + "/" + refType.getType() + "/" +
			reference;

		HttpURLConnection con = null;

		try
		{
			final URL obj = new URL(url);
			if (cloudURL.contains("https"))
			{
				setupConnection();
				con = (HttpsURLConnection)obj.openConnection();
			}
			else
			{
				con = (HttpURLConnection)obj.openConnection();
			}
			con.setRequestMethod("GET");
			con.setDoOutput(true);
			con.setConnectTimeout(5000000);
			con.setReadTimeout(5000000);
			con.setRequestProperty("Content-Type", "application/json");
			con.connect();
			final BufferedReader in = new BufferedReader(new InputStreamReader(
				con.getInputStream(), "UTF-8"));
			String inputLine;
			final StringBuilder response = new StringBuilder();
			while ((inputLine = in.readLine()) != null)
			{
				response.append(inputLine);
			}
			in.close();
			if (con.getResponseCode() == 200)
			{
				System.out.println(df.format(new Date()) + " APICON:GET -> Type:" +
					type.getReference() + " JSON=" + obj.toString());
				return response.toString();
			}
			else
			{
				System.out.println(df.format(new Date()) + " ERR: APICON:GET -> Type:" +
					type.getReference() + " JSON=" + obj.toString());
				System.out.println("Error: " + con.getResponseMessage() + ":" +
					con.getResponseCode());
				return null;
			}
		}
		catch (final IOException e)
		{
			throw new ApiNotReachableException(url, e);
		}
		finally
		{
			if (con != null)
				con.disconnect();
		}
	}

	/**
	 * saves a JSONArray in the Cloud
	 * 
	 * @param type
	 * @param obj
	 * @return
	 */
	public String postData(final DataType type, final JSONArray obj)
	{
		String slash = "";
		if (!cloudURL.endsWith("/"))
			slash = "/";
		final String url = cloudURL + slash + token + "/" + type.getReference() + "/saveAll/";
		try
		{
			final URL posturl = new URL(url);
			HttpURLConnection con;
			if (cloudURL.contains("https"))
			{
				setupConnection();
				con = (HttpsURLConnection)posturl.openConnection();
			}
			else
			{
				con = (HttpURLConnection)posturl.openConnection();
			}
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			con.setConnectTimeout(3000000);
			con.setReadTimeout(3000000);
			con.setUseCaches(false);
			con.setRequestProperty("Content-Type", "application/json");
			con.connect();

			final OutputStream out = con.getOutputStream();
			final OutputStreamWriter wr = new OutputStreamWriter(out, "UTF-8");
			wr.write(obj.toString());
			wr.flush();
			wr.close();
			if (out != null)
				out.close();
			if (con.getResponseCode() == 200)
			{
				final BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream(), "UTF-8"));
				String inputLine;
				final StringBuilder response = new StringBuilder();
				while ((inputLine = in.readLine()) != null)
				{
					response.append(inputLine);
				}
				in.close();
				System.out.println(df.format(new Date()) + " APICON:POST -> Type:" +
					type.getReference());
				con.disconnect(); // Disconnect
				return response.toString();
			}
			else
			{
				final BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream(), "UTF-8"));
				String inputLine;
				final StringBuilder response = new StringBuilder();
				while ((inputLine = in.readLine()) != null)
				{
					response.append(inputLine);
				}
				in.close();

				System.out.println(df.format(new Date()) + " ERR: APICON:POST -> Type:" +
					type.getReference());

				con.disconnect(); // Disconnect
				System.out.println("Error: " + con.getResponseMessage() + ":" +
					con.getResponseCode());
				return response.toString();
			}
		}
		catch (final IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * saves a JSONObject in the Cloud
	 * 
	 * @param type
	 * @param obj
	 * @return
	 */
	public String postData(final DataType type, final JSONObject obj)
	{

		String slash = "";
		if (!cloudURL.endsWith("/"))
			slash = "/";
		final String url = cloudURL + slash + token + "/" + type.getReference() + "/save/";
		try
		{
			final URL posturl = new URL(url);
			HttpURLConnection con;
			if (cloudURL.contains("https"))
			{
				setupConnection();
				con = (HttpsURLConnection)posturl.openConnection();
			}
			else
			{
				con = (HttpURLConnection)posturl.openConnection();
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
				out.close();
			if (con.getResponseCode() == 200)
			{
				final BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream(), "UTF-8"));
				String inputLine;
				final StringBuilder response = new StringBuilder();
				while ((inputLine = in.readLine()) != null)
				{
					response.append(inputLine);
				}
				in.close();

				System.out.println(df.format(new Date()) + " APICON:POST -> Type:" +
					type.getReference() + " JSON=" + obj.toString());
				con.disconnect(); // Disconnect
				return response.toString();
			}
			else
			{
				final BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream(), "UTF-8"));
				String inputLine;
				final StringBuilder response = new StringBuilder();
				while ((inputLine = in.readLine()) != null)
				{
					response.append(inputLine);
				}
				in.close();

				System.out.println(df.format(new Date()) + " ERR: APICON:POST -> Type:" +
					type.getReference() + " JSON=" + obj.toString());

				con.disconnect(); // Disconnect
				System.out.println("Error: " + con.getResponseMessage() + ":" +
					con.getResponseCode());
				return response.toString();
			}
		}
		catch (final IOException e)
		{
			e.printStackTrace();
			return null;
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
			e.printStackTrace();
		}
	}
}
