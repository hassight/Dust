package business.spring;

public class SpringIoC {
	private static final ApplicationContext context = new ClassPathXmlApplicationContext("business/spring/spring.xml");
	
	public static Object getBean(String beanName) {
		return context.getBean(beanName);
	}
}