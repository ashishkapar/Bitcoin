import java.net.http.HttpClient
import java.net.URI
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import kotlinx.serialization.json.Json
import kotlinx.serialization.Serializable
import kotlin.system.exitProcess

@Serializable
data class BitcoinObject(
    val bitcoin: BitcoinPriceInUSD
)
@Serializable
data class BitcoinPriceInUSD(
    val usd: Double
)


fun main(){
    val client = HttpClient.newHttpClient()
    val request = HttpRequest.newBuilder().uri(URI.create("https://api.coingecko.com/api/v3/simple/price?ids=bitcoin&vs_currencies=usd")).build()

    val response = client.send(request, HttpResponse.BodyHandlers.ofString())

    val statusCode = response.statusCode()
    println("Status Code: $statusCode")
    if (statusCode != 200){

        exitProcess(0)
    }
    val data = Json.decodeFromString<BitcoinObject>(response.body())
    val currentBTC : Double = data.bitcoin.usd
    val userConvertedBTC: Double
    println("1 BTC: $currentBTC")
    print("\nEnter price in USD: ")
    val userInput: Double = readlnOrNull()?.toDoubleOrNull()?: 0.00
    val bitcoinPerDollar = 1.00/currentBTC
    userConvertedBTC = bitcoinPerDollar*userInput
    println("$userInput USD = $userConvertedBTC BTC")
}