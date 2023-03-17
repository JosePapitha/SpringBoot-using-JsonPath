package com.jayway.jsonpath;

import java.io.InputStream;
import java.nio.charset.Charset;

import org.testng.annotations.Test;

import com.jayway.jsonpath.spi.transformer.TransformationSpec;


public class TransformationBasicTest {

    InputStream sourceStream;
    InputStream transformSpec;
    Configuration configuration;
    TransformationSpec spec;
    Object sourceJson;


    public void setup() {
        configuration = Configuration.builder()
                .options(Option.CREATE_MISSING_PROPERTIES_ON_DEFINITE_PATH).build();
        sourceStream = this.getClass().getClassLoader().getResourceAsStream("transforms/transformsource.json");
        sourceJson = configuration.jsonProvider().parse(sourceStream, Charset.defaultCharset().name());

        DocumentContext jsonContext = null;
		try {
			jsonContext = JsonPath.parse(sourceJson);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("Document Input :" + jsonContext.jsonString());

        transformSpec = this.getClass().getClassLoader().getResourceAsStream("transforms/transformspec.json");

        spec = configuration.transformationProvider().spec(transformSpec, configuration);

    }

    @Test
    public void simple_transform_spec_test() {
    	setup();
        Object transformed = configuration.transformationProvider().transform(sourceJson,spec, configuration);
        DocumentContext jsonContext = JsonPath.parse(transformed);
        String path = "$.shipment.unloading.location";

        System.out.println("Document Created by Transformation:" + jsonContext.jsonString());
    }

}
