package org.geekbang.homework;

import static java.util.ServiceLoader.load;
import static org.springframework.beans.factory.support.BeanDefinitionBuilder.genericBeanDefinition;

import java.util.ServiceLoader;
import org.geekbang.homework.beans.factory.StudentFactory;
import org.geekbang.homework.config.StudentConfig;
import org.geekbang.homework.entity.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.util.StringUtils;

// 使用spring的测试框架
@SpringJUnitConfig
@ContextConfiguration(classes = {StudentConfig.class})
public class SpringProjectTest {

    @Autowired
    @Qualifier("student-by-configuration-java-bean")
    Student student;

    @BeforeEach // 类似于junit4的@Before
    public void before() {
        System.out.println("Before");
    }

    @AfterEach // 类似于junit4的@After
    public void after() {
        System.out.println("After");
    }

    @Test
    void testBeanFactoryGetBean()  {
        // 配置 xml 配置文件
        // 启动 Spring 应用上下文，获取 BeanFactory 对象
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("application-context.xml");

        //根据 name 获取 xml 配置文件中的 bean
        Student xmlStudentByName = (Student) beanFactory.getBean("xmlStudent");
        System.out.println("xmlStudentByName = " + xmlStudentByName);

        //根据 alias 获取 xml 配置文件中的 bean
        Student xmlStudentByAlias = (Student) beanFactory.getBean("xml-student");
        System.out.println("xmlStudentByAlias = " + xmlStudentByAlias);

        //判断 xmlStudentByName 和 xmlStudentByAlias 是否一致
        System.out.println("xmlStudentByName == xmlStudentByAlias : " + (xmlStudentByName == xmlStudentByAlias));

        //根据 type 获取 xml 配置文件中的 bean
        Student xmlStudentByType = beanFactory.getBean(Student.class);
        System.out.println("xmlStudentByType = " + xmlStudentByType);

        //根据 ObjectFactory 延迟实例化 Bean
        ObjectFactory<Student> objectFactory = (ObjectFactory<Student>) beanFactory.getBean("objectFactory");
        Student xmlStudentByObjectFactory = objectFactory.getObject();
        System.out.println("xmlStudentByObjectFactory：" + xmlStudentByObjectFactory);

        //静态工厂方法 - 实例化 Bean
        Student student = beanFactory.getBean("student-by-static-method", Student.class);
        System.out.println(student);

        //实例工厂方法 - 实例化 Bean
        Student studentByInstanceMethod = beanFactory.getBean("student-by-instance-method", Student.class);
        System.out.println(studentByInstanceMethod);

        //FactoryBean - 实例化 Bean
        Student studentByFactoryBean = beanFactory.getBean("student-by-factory-bean", Student.class);
        System.out.println(studentByFactoryBean);

        System.out.println(student == studentByInstanceMethod);
        System.out.println(student == studentByFactoryBean);
    }

    @Test
    void testClassPathXmlApplicationContextGetBean()  {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application-context.xml");
        Student xmlStudent = (Student) applicationContext.getBean("xmlStudent");
        System.out.println("student = " + xmlStudent);
    }


    @Test
    void testAutowired()  {
        System.out.println("student = " + student);
    }

    @Test
    void testAnnotationConfigApplicationContextGetBean()  {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(StudentConfig.class);
        applicationContext.refresh();
        Student student = applicationContext.getBean(Student.class);
        System.out.println("student = " + student);
    }

    @Test
    void testServiceLoader(){
//        // 配置 xml 配置文件
//        // 启动 Spring 应用上下文，获取 BeanFactory 对象
//        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application-context.xml");
//
//        // 通过 ApplicationContext 获取 AutowireCapableBeanFactory
//        AutowireCapableBeanFactory beanFactory = applicationContext.getAutowireCapableBeanFactory();
//        ServiceLoader<StudentFactory> serviceLoader = beanFactory.getBean("studentFactoryServiceLoader", ServiceLoader.class);
        ServiceLoader<StudentFactory> serviceLoader = load(StudentFactory.class, Thread.currentThread().getContextClassLoader());
        displayServiceLoader(serviceLoader);
    }

    @Test
    void testBeanDefinitionBuilder(){
        // 1.通过 BeanDefinitionBuilder 构建
        String studentByBeanDefinitionBuilderName = "student-by-bean-definition-builder";
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(Student.class);
        // 通过属性设置
        beanDefinitionBuilder
            .addPropertyValue("id", 1)
            .addPropertyValue("name", studentByBeanDefinitionBuilderName);


        // 2.通过 AbstractBeanDefinition 以及派生类
        String studentByGenericBeanDefinitionName = "student-by-bean-generic-bean-definition";

        GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition();
        // 设置 Bean 类型
        genericBeanDefinition.setBeanClass(Student.class);
        // 通过 MutablePropertyValues 批量操作属性
        MutablePropertyValues propertyValues = new MutablePropertyValues();
        propertyValues
            .add("id", 2)
            .add("name", studentByGenericBeanDefinitionName);
        // 通过 set MutablePropertyValues 批量操作属性
        genericBeanDefinition.setPropertyValues(propertyValues);

        //3.启动 spring 上下文，获取 bean
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(SpringProjectTest.class);
        // 命名 Bean 注册方法
        registerBeanDefinition(applicationContext, beanDefinitionBuilder.getBeanDefinition(), studentByBeanDefinitionBuilderName);
        registerBeanDefinition(applicationContext, genericBeanDefinition, studentByGenericBeanDefinitionName);
        // 非命名 Bean 注册方法
        BeanDefinitionReaderUtils.registerWithGeneratedName(beanDefinitionBuilder.getBeanDefinition(), applicationContext);
        BeanDefinitionReaderUtils.registerWithGeneratedName(genericBeanDefinition, applicationContext);
        applicationContext.refresh();

        System.out.println("Student 类型的所有 Beans" + applicationContext.getBeansOfType(Student.class));

        Student studentByBeanDefinitionBuilder = applicationContext.getBean(studentByBeanDefinitionBuilderName, Student.class);
        System.out.println(studentByBeanDefinitionBuilderName + " : " + studentByBeanDefinitionBuilder);

        Student studentByGenericBeanDefinition = applicationContext.getBean(studentByGenericBeanDefinitionName, Student.class);
        System.out.println(studentByGenericBeanDefinitionName + " : " + studentByGenericBeanDefinition);

        // 显示地关闭 Spring 应用上下文
        applicationContext.close();
    }

    public static void registerBeanDefinition(BeanDefinitionRegistry registry, AbstractBeanDefinition beanDefinition, String beanName) {
        // 判断如果 beanName 参数存在时
        if (StringUtils.hasText(beanName)) {
            // 注册 BeanDefinition
            registry.registerBeanDefinition(beanName, beanDefinition);
        } else {
            // 非命名 Bean 注册方法
            BeanDefinitionReaderUtils.registerWithGeneratedName(beanDefinition, registry);
        }
    }

    public static void registerBeanDefinition(BeanDefinitionRegistry registry, AbstractBeanDefinition beanDefinition) {
        registerBeanDefinition(registry, beanDefinition,null);
    }

    private static void displayServiceLoader(ServiceLoader<StudentFactory> serviceLoader) {
        for (StudentFactory userFactory : serviceLoader) {
            System.out.println("student-by-service-loader :" + userFactory.instance());
        }
    }
}
