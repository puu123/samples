package api.impl;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;

import api.Bar;

@RequestScoped
@Default
public class BarImpl1 implements Bar {

	@Override
	public String bar() {
		return "bar1";
	}

}
