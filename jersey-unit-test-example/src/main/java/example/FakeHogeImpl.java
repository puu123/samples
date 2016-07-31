package example;

//import javax.enterprise.context.ApplicationScoped;

import foo.Hoge;

//\\@ApplicationScoped

public class FakeHogeImpl implements Hoge {

	@Override
	public String greeting() {
		return "fake hogeeee!!1";
	}

}
