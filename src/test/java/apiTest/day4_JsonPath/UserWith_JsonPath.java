package apiTest.day4_JsonPath;
import static io.restassured.RestAssured.*;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.baseURI;

public class UserWith_JsonPath {

    @BeforeClass
    public void beforeClass(){

        baseURI="https://www.krafttechexlab.com/sw/api/v1";
    }

  /*
    TASK
    Given accept type is json
    And Path param user id is 111
    When user sends a GET request to /allusers/getbyid/{id}
    Then the status Code should be 200
    And Content type json should be "application/json; charset=UTF-8"
    And user's name should be Thomas Eduson
    And user's id should be 111
    And user's email should be thomas@test.com
   */

    @Test
            public void test(){

        Response response=given().accept(ContentType.JSON)
                .pathParam("id",111)
                .when()
                .get("/allusers/getbyid/{id}");

        Assert.assertEquals(response.statusCode(),200);
        Assert.assertEquals(response.contentType(),"application/json; charset=UTF-8");

        JsonPath jsonPath=response.jsonPath();

        int idJson= jsonPath.getInt("id[0]");
        String nameJson= jsonPath.getString("name[0]");
        String emailJson= jsonPath.getString("email[0]");

        Assert.assertEquals(idJson,111);
        Assert.assertEquals(nameJson, "Thomas Eduson");
        Assert.assertEquals(emailJson,"thomas@test.com");


    }

      /*
    TASK
    Given accept type is json
    When user sends a GET request to /allusers/alluser
    Then the status Code should be 200
    And Content type json should be "application/json; charset=UTF-8"
    And second name should be isa akyuz
    And first user's experience jobs should be Junior Developer1, Junior Developer1, Junior Developer
     */

    @Test
    public void test2(){
        Response response= given().accept(ContentType.JSON)
                .queryParam("pagesize",50)
                .queryParam("page",1)
                .when()
                .get("/allusers/alluser");

        Assert.assertEquals(response.statusCode(),200);
        Assert.assertEquals(response.contentType(),"application/json; charset=UTF-8");

        JsonPath jsonPath=response.jsonPath();

        String secondName= jsonPath.getString("name[1]");
        List<String> jobs=jsonPath.getList("experience.job[0]");

        System.out.println(secondName);
        System.out.println(jobs);


        List<String> joblist = new ArrayList<>();
        joblist.add("Junior Developer1");
        joblist.add("Junior Developer1");
        joblist.add("Junior Developer");

        System.out.println(joblist);


        Assert.assertEquals(secondName,"isa akyuz");
       Assert.assertEquals(jobs,joblist);


    }

    /*
    TASK
    Given accept type is json
    And Path param user id is 111
    When user sends a GET request to /allusers/getbyid/{id}
    Then the status Code should be 200
    And Content type json should be "application/json; charset=UTF-8"
    Get user skills
    And user's first skill should be PHP

   */

    @Test
    public void test4(){

        Response response= given().accept(ContentType.JSON)
                .pathParam("id",111)
                .when().get("/allusers/getbyid/{id}");

        Assert.assertEquals(response.statusCode(),200);
        Assert.assertEquals(response.contentType(),"application/json; charset=UTF-8");

        //assign to jsonpath

        JsonPath jsonPath=response.jsonPath();

        //jsonpath metodu liste olarak stringe assign etmeye izin veriyor.
        String skills= jsonPath.getString("skills");

        //path metodu liste olarak Stringe assign etmeye izin vermiyor.
//        String skillsPath= response.path("skills");
//        System.out.println(skillsPath);

        String firstSkill= jsonPath.getString("skills[0][0]");

        System.out.println(skills);

        System.out.println(firstSkill);


    }




}
