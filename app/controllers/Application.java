package controllers;

import model.NfaureTest;
import play.*;
import play.db.jpa.JPA;
import play.libs.F;
import play.libs.WS;
import play.mvc.*;

import views.html.*;

import javax.persistence.LockModeType;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Application extends Controller {

    public static Result index() {

        Boolean mustContinue=true;
        List<F.Promise<? extends WS.Response>> promises = new ArrayList<>();
        while(mustContinue){

            F.Promise<WS.Response> promise = WS.url("http://test3.mclics-ads.melifrontends.com/advertising/product_ads/ads/MLA674643476")
                    .setHeader("X-Caller-Scopes", "admin")
                    .post("");
            promises.add(promise);

            if(promises.size() % 1000 == 0){
                Logger.info("Waiting 1000 promises");
                F.Promise.sequence(promises).get(10, TimeUnit.MINUTES);
                promises.clear();
            }
        }

        return ok(index.render("Your new application is ready."));
    }


    public static Result updateWithSleep(){
        JPA.withTransaction(()->{
            System.out.println("BEGIN updateWithSleep");
            NfaureTest entity = new NfaureTest();
            entity.setCustomerId(1L);
            entity.setCustomerName("UPDATEWITHSLEEP");
            entity.setCustomerCity("UPDATEWITHSLEEP");
            JPA.em().merge(entity);
            System.out.println("WAITING updateWithSleep");
            sleep(20000L);
            System.out.println("COMMITING updateWithSleep");
        });

        return ok("updateWithSleep");
    }

    public static Result update(){
        JPA.withTransaction(()->{
            System.out.println("BEGIN update");
            NfaureTest entity = new NfaureTest();
            entity.setCustomerId(1L);
            entity.setCustomerName("UPDATE");
            entity.setCustomerCity("UPDATE");
            JPA.em().merge(entity);
            System.out.println("COMMITING update");
        });

        return ok("update");
    }


    public static Result updateWithSleepAndLock(){
        JPA.withTransaction(()->{
            System.out.println("BEGIN updateWithSleepAndLock");
            NfaureTest entity = JPA.em().find(NfaureTest.class,1L);
            JPA.em().lock(entity,LockModeType.PESSIMISTIC_READ);
            entity.setCustomerId(1L);
            entity.setCustomerName("UPDATEWITHSLEEP");
            entity.setCustomerCity("UPDATEWITHSLEEP");
            JPA.em().merge(entity);
            System.out.println("WAITING updateWithSleepAndLock");
            sleep(20000L);
            System.out.println("COMMITING updateWithSleepAndLock");
        });

        return ok("updateWithSleep");
    }

    public static Result read(){
        JPA.withTransaction(()->{
            NfaureTest entity = JPA.em().find(NfaureTest.class,1L);
            System.out.println("ENTITY================>" + entity.getCustomerCity());
        });

        return ok("");
    }


    public static Result updateWithLock(){
        JPA.withTransaction(()->{
            System.out.println("BEGIN updateWithLock");
            NfaureTest entity = JPA.em().find(NfaureTest.class,1L,LockModeType.PESSIMISTIC_READ);
            entity.setCustomerId(1L);
            entity.setCustomerName("UPDATE");
            entity.setCustomerCity("UPDATE");
            JPA.em().merge(entity);
            System.out.println("COMMITING updateWithLock");
        });

        return ok("update");
    }



    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static Result move() {

        Boolean mustContinue=true;
        List<F.Promise<? extends WS.Response>> promises = new ArrayList<>();
        while(mustContinue){

            String campaign1 = "{\"campaign_id\":161452821}";
            String campaign2 = "{\"campaign_id\":161412828}";

            F.Promise<WS.Response> promise = WS.url("http://test3.mclics-ads.melifrontends.com/advertising/product_ads/ads/MLA674696410")
                    .setHeader("x-caller-scopes", "admin")
                    .setHeader("x-caller-id", "264487025")
                    .setHeader("Content-Type", "application/json")
                    .put(campaign2).flatMap(response -> {
                        System.out.println("STATUS=>" + response.getStatus());
                        System.out.println("STATUS=>" + response.getBody());
                        return F.Promise.pure(response);
                    });
            promises.add(promise);

            F.Promise<WS.Response> promise2 = WS.url("http://test3.mclics-ads.melifrontends.com/advertising/product_ads/ads/MLA674696410")
                    .setHeader("X-Caller-Scopes", "admin")
                    .setHeader("Content-Type", "application/json")
                    .put(campaign1);
            promises.add(promise2);

            if(promises.size() % 100 == 0){
                Logger.info("Waiting 1000 promises");
                F.Promise.sequence(promises).get(10, TimeUnit.MINUTES);
                promises.clear();
            }
        }

        return ok(index.render("Your new application is ready."));
    }

}
