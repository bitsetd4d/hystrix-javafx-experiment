package com.bitsetd4d.controller.internal;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class HystrixExperimentConfigurationImplTest {

    private HystrixExperimentConfigurationImpl configuration;

    @Before
    public void setUp() throws Exception {
        configuration = new HystrixExperimentConfigurationImpl();
    }

    @Test
    public void hystrixConfigurationProperty() throws Exception {
        ObjectProperty<List<String>> myProperty = new SimpleObjectProperty<>();
        myProperty.bindBidirectional(configuration.hystrixConfigProperty());

        List<String> expected1 = Arrays.asList("a", "b", "c");
        myProperty.set(expected1);
        assertThat(configuration.hystrixConfigProperty().get(), is(equalTo(expected1)));
        assertThat(configuration.getHystrixConfig(), is(equalTo(expected1)));

        List<String> expected2 = Arrays.asList("g", "h", "i");
        configuration.hystrixConfigProperty().set(expected2);
        assertThat(myProperty.get(), is(equalTo(expected2)));
        assertThat(configuration.getHystrixConfig(), is(equalTo(expected2)));

        List<String> expected3 = Arrays.asList("g", "h", "i");
        configuration.setHystrixConfig(expected3);
        assertThat(configuration.hystrixConfigProperty().get(), is(equalTo(expected3)));
        assertThat(configuration.getHystrixConfig(), is(equalTo(expected3)));
    }
}