package ledg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.IOException;

import static java.lang.Runtime.getRuntime;

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling

public class Application //extends javafx.application.Application
{

    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);
//        String[] beanNames = ctx.getBeanDefinitionNames();


        System.out.println(String.format("stdweb:FreeMemory %s, TotalMemory %s, 0.3 of totmem: %s",
                getRuntime().freeMemory()/1024,getRuntime().totalMemory()/1024,getRuntime().totalMemory()/1024*0.3
        ));

        System.out.println("spring main finish");
       // launch(args);
    }
    //@Override
//    public void start(Stage primaryStage) throws IOException, HashDecodeException {
//
//        DesktopController controller=new DesktopController();
//
//        FXMLLoader fxmlLoader = new FXMLLoader(Charset.defaultCharset());
//        fxmlLoader.setController(controller);
//        Parent root = (Parent) fxmlLoader.load(this.getClass().getClassLoader().getResource("mainPanel.fxml"));
//        //Parent root = (Parent) FXMLLoader.load(this.getClass().getClassLoader().getResource("mainPanel.fxml"));
//
//        primaryStage.setTitle("Hello World");
//        primaryStage.setScene(new Scene(root, 800.0D, 600.0D));
//
//        primaryStage.show();
//    }
}
