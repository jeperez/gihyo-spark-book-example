/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jp.gihyo.spark.ch03.pairrdd_transformation

import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}

// scalastyle:off println

object SortByKeyExample {
  def main(args: Array[String]) {
    Logger.getLogger("org").setLevel(Level.WARN)

    val conf = new SparkConf().setAppName("SortByKeyExample")
    val sc = new SparkContext(conf)

    run(sc)
    sc.stop()
  }

  def run(sc: SparkContext) {
    val fruits = sc.parallelize(
      Array(("Apple", 6), ("Orange", 1), ("Apple", 2), ("Orange", 5), ("PineApple", 1)))
    val sortedByKeyAsc = fruits.sortByKey(ascending = false)

    println(s"""fruits:         ${fruits.collect().mkString(", ")}""")
    println(s"""sortedByKeyAsc: ${sortedByKeyAsc.collect().mkString(", ")}""")

    val nums = sc.parallelize(
      Array(("One", 1), ("Hundred", 100), ("Three", 3), ("Thousand", 1000)))
    implicit val sortByStrLen = new Ordering[String] {
      def compare(x: String, y: String): Int = x.length - y.length
    }
    val sortedByKeyLength = nums.sortByKey()

    println()
    println(s"""nums:              ${nums.collect().mkString(", ")}""")
    println(s"""sortedByKeyLength: ${sortedByKeyLength.collect().mkString(", ")}""")
  }
}

// scalastyle:on println
