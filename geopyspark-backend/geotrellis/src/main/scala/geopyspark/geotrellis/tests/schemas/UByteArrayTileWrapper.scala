package geopyspark.geotrellis.tests.schemas

import protos.tileMessages._
import geopyspark.geotrellis._
import geopyspark.geotrellis.testkit._
import geotrellis.raster._
import geotrellis.spark._

import org.apache.spark._
import org.apache.spark.rdd.RDD
import org.apache.spark.api.java.JavaRDD

object UByteArrayTileWrapper extends Wrapper2[Tile, ProtoTile] {
  def testOut(sc: SparkContext): JavaRDD[Array[Byte]] =
    PythonTranslator.toPython[Tile, ProtoTile](testRdd(sc))

  def testIn(rdd: RDD[Array[Byte]]) =
    PythonTranslator.fromPython[Tile, ProtoTile](rdd, ProtoTile.parseFrom)

  def testRdd(sc: SparkContext): RDD[Tile] = {
    val arr = Array(
      UByteArrayTile(Array[Byte](0, 0, 1, 1), 2, 2),
      UByteArrayTile(Array[Byte](1, 2, 3, 4), 2, 2),
      UByteArrayTile(Array[Byte](5, 6, 7, 8), 2, 2))
    sc.parallelize(arr)
  }
}
