import java.util.Scanner
//import org.apache.spark._
//import org.apache.spark.rdd._
//import org.apache.spark.SparkContext._
import scala.math._
import java.io.File
import scala.io.Source

import scala.collection.JavaConversions._  // enables foreach
import org.apache.commons.io.FileUtils._
import java.io.File

object query {
  def main(args: Array[String]){
//      Interactive input for users
//      Get the folder path
    print("Enter the input folder path: ") 
    val scanner = new java.util.Scanner(System.in) 
    val path = scanner.nextLine() 
    
//      Fetch filenames and read all files
    val dir = new File(path)
    val files = listFiles(dir, null, true)
    val filenames = listFiles(dir, null, true)
    .map(s => s.getAbsolutePath)
    val Text = files.map(s => readFileToString(s))
    val wholeText = (filenames, Text).zipped.toList

    print("Enter the query type, boolean or tfidf: ")
    val queryType = scanner.nextLine() 
    
    print("Enter the maximum number of results, if all results are need, please enter -1: ") 
    var num = scanner.nextLine().toInt

    print("Enter a queryString: ") 
    val queryString = scanner.nextLine() 
    
//    Return results according to the criteria
    if (queryType == "boolean"){
      val results = wholeText.map(s => booleanQuery(queryString, s._1, s._2)).filter(s => s != "")
      if (num == -1){
        results.foreach(println)
      }else{
        results.take(num).foreach(println)
      }
    }else if (queryType == "tfidf"){
      val numId = wholeText.map(s => s._2.split(" ").map(ss => ss.trim))
      .map(s => if (s.contains(queryString)) 1 else 0)
      .reduce(_+_)
      val idf = math.log(wholeText.length / (numId.toDouble + 1))  // Calculate idf 
      val results = wholeText.map(s => tfidfQuery(queryString, s._1, s._2, idf))
      .sortBy(s => -1 * s._2).map(s => s._1)
      if (num == -1){
        results.foreach(println)
      }else{
        results.take(num).foreach(println)
      }
    }
    
    //      Define the boolean query function
    def booleanQuery(queryString: String, filename: String, content: String) = {
//      Assume that all the words are split by space
//      s.trim: omit all the spaces for all the words after splitting
      val wordsAll = content.toLowerCase().split(" ").map(s => s.trim)  
      
//      For example, the queryString: "science and religion or happy and flower or morality"
//      1. Split by " OR ": Array("science and religion", "happy and flower", "morality")
//      2. Split by " AND ": Array(Array("science", "religion"), Array("happy", "flower"), Array("morality")) 
//      3. Determine true if the content words include query word, otherwise false. Assume that for the content of one document, it returns:
//            Array(Array(true, false), Array(false, false), Array(true))
//      4. The boolean operation of the inner array is "and", 
//          so if the inner array contains false, then it returns false, then the result is:
//            Array(false, false, true)
//      5. Determine the outer array by or, it returns:
//          false or false or true -> true
//      6. If the search is true, return the filename, otherwise "".
      
      val booleanOrList = queryString.toLowerCase().filterNot(_ == '"').split(" or ") // 1
      .map(s => s.trim.split(" and ").map(ss => ss.trim)) //2
      .map(s => s.map(ss => wordsAll.contains(ss))) //3
      .map(s => if (s.contains(false)) false else true) //4
      val trueOrFalse = if (booleanOrList.contains(true)) true else false //5
      if ( trueOrFalse ){filename}else {""} //6
    }
    
//      Define the tfidf scoring method
//      idf value is calculated for each queryString
//      7. Omit the double quote marks from queryString
//      8. For each document, split the words
//      9. Count the times of the queryString appears in the document
//      10. Calculate tf
//      11. Calculate tfidf
    def tfidfQuery(queryString: String, filename: String, content: String, idf: Double) = {
      val queryStringNorm = queryString.toLowerCase().filterNot(_ == '"') //7
      val wordsAll = content.toLowerCase().split(" ").map(s => s.trim) //8
      val count = wordsAll.filter(s => s == queryStringNorm).length //9
      val tf = count / wordsAll.length.toDouble //10
      val tfidf = tf * idf //11
      (filename, tfidf)
    }
  }
}
