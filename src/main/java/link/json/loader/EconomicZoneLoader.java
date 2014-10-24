package link.json.loader;

import java.text.ParseException;

import link.json.AbstractHasNameJsonLoader;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import domain.DataType;
import domain.EconomicZone;

public class EconomicZoneLoader extends AbstractHasNameJsonLoader<EconomicZone>
{

	public EconomicZoneLoader(final DataType dataType, final String cloudUrl, final String token)
	{
		super(dataType, cloudUrl, token);
	}

	@Override
	public JSONObject toJSON(final EconomicZone value) throws JSONException
	{
		final JSONObject obj = super.appendTheJson(value);
		return obj;
	}

	@Override
	public EconomicZone fromJSON(final JSONObject obj) throws JSONException, ParseException
	{
		final EconomicZone economicZone = EconomicZone.fromJSON(obj);
		return economicZone;
	}

}
