package foo;

import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.reflect.ClassPath;

public class ClassMain {

	public static void main(String[] args) throws Exception {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Set<Class<?>> allClasses = ClassPath.from(loader)
		        //.getTopLevelClasses("foo").stream()
		        .getAllClasses().stream()
		        .map(info -> info.load())
		        .collect(Collectors.toSet());
		System.out.println(allClasses.size());

	}

}
