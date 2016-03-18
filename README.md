### Aim: Build a search engine to 
1. Support boolean query with "AND" and "OR".
2. Support tf-idf ranking query.

### Solution:
1. Read all the data to a list of tuples, each tuple contains filename and content.
2. Operate functions on this list.
    - For boolean query, split the queryString by "or", then by "and". For each tuple in the list, booleanQuery function can be applied. 
    - For tf-idf ranking query, first idf can be calculated through whole text for a query term, then the tf score for each context can be calculated. For each tuple in the list, tf-idf score can be computed, thus, filenames can be ranked by tf-idf scores.
3. Additional consideration:
    - Only print a maximum number of results. 

### How to run 
Scala-2.10 is required. There are three versions for this searching engine:

###### 1. queryScala.scala (require external package: commons-io-2.4.jar).
This code is for a single node task query. It does not need to be compiled, can be executed from a console like this:


    scala -classpath commons-io-2.4.jar queryScala.scala
    
###### 2. query.scala  (require external package: commons-io-2.4.jar). 

This code is for a single node task query. If you want to run the code with Eclipse, make sure you also build path by adding external archives which are also listed here. 

###### 3. querySpark.scala (require spark-assembly-1.3.0-hadoop1.0.4.jar).

If you have installed spark, and spark-submit is executable, querySpark is highly recommended as this code is scalable. You can run the code with Eclipse by adding external package 'spark-assembly-1.3.0-hadoop1.0.4.jar' or you can execute it with 'spark-submit' from a console after compiling the code into a jar file. For example: 


    spark-submit --class com.beifei.searchEngine.querySpark --properties-file spark-1.3.0/conf/spark-defaults.conf --jars spark-assembly-1.3.0-hadoop1.0.4.jar querySpark.jar

### Execute example

    scala -classpath commons-io-2.4.jar queryScala.scala
    Enter the input folder path: /Users/Alice_Maple/Beifei/projects/MailOnline/data/20_newsgroups
    Enter the query type, boolean or tfidf: boolean
    Enter the maximum number of results, if all results are need, please enter -1: 20
    Enter a queryString: science and flower or religion


    /Users/Alice_Maple/Beifei/projects/MailOnline/data/20_newsgroups/alt.atheism/49960
    /Users/Alice_Maple/Beifei/projects/MailOnline/data/20_newsgroups/alt.atheism/51060
    /Users/Alice_Maple/Beifei/projects/MailOnline/data/20_newsgroups/alt.atheism/51122
    /Users/Alice_Maple/Beifei/projects/MailOnline/data/20_newsgroups/alt.atheism/51130
    /Users/Alice_Maple/Beifei/projects/MailOnline/data/20_newsgroups/alt.atheism/51164
    /Users/Alice_Maple/Beifei/projects/MailOnline/data/20_newsgroups/alt.atheism/51184
    /Users/Alice_Maple/Beifei/projects/MailOnline/data/20_newsgroups/alt.atheism/51229
    /Users/Alice_Maple/Beifei/projects/MailOnline/data/20_newsgroups/alt.atheism/51233
    /Users/Alice_Maple/Beifei/projects/MailOnline/data/20_newsgroups/alt.atheism/51234
    /Users/Alice_Maple/Beifei/projects/MailOnline/data/20_newsgroups/alt.atheism/51235
    /Users/Alice_Maple/Beifei/projects/MailOnline/data/20_newsgroups/alt.atheism/51237
    /Users/Alice_Maple/Beifei/projects/MailOnline/data/20_newsgroups/alt.atheism/51238
    /Users/Alice_Maple/Beifei/projects/MailOnline/data/20_newsgroups/alt.atheism/51243
    /Users/Alice_Maple/Beifei/projects/MailOnline/data/20_newsgroups/alt.atheism/51261
    /Users/Alice_Maple/Beifei/projects/MailOnline/data/20_newsgroups/alt.atheism/51275
    /Users/Alice_Maple/Beifei/projects/MailOnline/data/20_newsgroups/alt.atheism/51300
    /Users/Alice_Maple/Beifei/projects/MailOnline/data/20_newsgroups/alt.atheism/51315
    /Users/Alice_Maple/Beifei/projects/MailOnline/data/20_newsgroups/alt.atheism/51318
    /Users/Alice_Maple/Beifei/projects/MailOnline/data/20_newsgroups/alt.atheism/52499
    /Users/Alice_Maple/Beifei/projects/MailOnline/data/20_newsgroups/alt.atheism/53066

    

### How to improve the search engine
1. Allow  for multiple queries / batches of queries.
2. Depending on search words requirements, words can be extracted including removing titles (only concentrating on the main content), removing non-letters, omitting stop words. For this task, it did not specify these requirements, so a general search engine is better, as a query can be in titles or a stop word.
3. The ranking method can be improved, as tf-idf does not capture position in text, semantics, co-occurrences in different documents. Latent Semantic Indexing or LDA can be used to improve the ranking model.

