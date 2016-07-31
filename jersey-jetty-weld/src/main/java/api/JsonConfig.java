package api;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Provider
public class JsonConfig implements ContextResolver<ObjectMapper>
{
	final ObjectMapper mapper;

	public JsonConfig()
	{
		mapper = new ObjectMapper();
//		mapper.registerModule(new JaxbAnnotationModule());
//		mapper.registerModule(new JodaModule());
		mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		mapper.setSerializationInclusion(Include.NON_NULL);
//		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	}

	@Override
	public ObjectMapper getContext(Class<?> type)
	{
		return mapper;
	}
}