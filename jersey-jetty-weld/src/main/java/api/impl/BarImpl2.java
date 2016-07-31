package api.impl;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;

import api.Bar;

@ApplicationScoped
public class BarImpl2 
//implements Bar 
{

	//@Override
	public String bar() {
		return "bar2";
	}

}
