import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class JavaTest {

    @Test
    public void displayUserAccount_OK_StatusCode200(){

        given().when().get("/account/1").then().statusCode(200);
    }

    @Test
    public void displayUserAccount_UserDoesntExist_StatusCode204(){
        given().when().get("/account/888").then().statusCode(204);
    }

    @Test
    public void sellPLNBuyEUR_OK_StatusCode200(){
        given().header("accountId","1").pathParam("currency","EUR").pathParam("amount","10").when().put("/sell/PLN/{currency}/{amount}").then().statusCode(200);

    }

    @Test
    public void sellPLNBuyEUR_NotEnoughCurrency_StatusCode406(){
        given().header("accountId","1").pathParam("currency","EUR").pathParam("amount","10000").when().put("/sell/PLN/{currency}/{amount}").then().statusCode(406);
    }

    @Test
    public void sellPLNBuyEUR_WrongCurrency_StatusCode400(){
        given().header("accountId","1").pathParam("currency","FRT").pathParam("amount","1").when().put("/sell/PLN/{currency}/{amount}").then().statusCode(400);
    }

     @Test
    public void buyPLNSellUSD_OK_StatusCode200(){
        given().header("accountId","1").pathParam("currency","EUR").pathParam("amount","10").when().put("/buy/PLN/{currency}/{amount}").then().statusCode(200);

    }

    @Test
    public void buyPLNSellGBP_NotEnoughCurrency_StatusCode406(){
        given().header("accountId","1").pathParam("currency","GBP").pathParam("amount","10000").when().put("/buy/PLN/{currency}/{amount}").then().statusCode(406);
    }

    @Test
    public void buyPLNSellEUR_WrongCurrency_StatusCode400(){
        given().header("accountId","1").pathParam("currency","FRT").pathParam("amount","1").when().put("/sell/PLN/{currency}/{amount}").then().statusCode(400);
    }

}
