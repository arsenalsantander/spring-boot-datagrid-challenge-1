package com.redhat.developergames;

import com.redhat.developergames.model.Weather;
import com.redhat.developergames.repository.WeatherRepository;
import org.infinispan.spring.remote.session.configuration.EnableInfinispanRemoteHttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;


@SpringBootApplication
@RestController
@EnableCaching
@EnableInfinispanRemoteHttpSession
@SessionAttributes("sessions")
public class WeatherApp {

   @Autowired
   WeatherRepository weatherRepository;

   @GetMapping("/")
   public String index() {
      return "Greetings from Spring Boot with Data Grid!";
   }

   @GetMapping("/weather/{location}")
   public Object getByLocation(@PathVariable String location, HttpSession session) {
      Weather weather = weatherRepository.getByLocation(location);
      if (weather == null) {
         return String.format("Weather for location %s not found", location);
      }

      session.setAttribute("latest", weather);

      return weather;
   }

   @GetMapping("/latest")
   public Object latestLocation(HttpSession session) {
      return session.getAttribute("latest");
   }

   public static void main(String... args) {
      new SpringApplicationBuilder().sources(WeatherApp.class).run(args);
   }
}
