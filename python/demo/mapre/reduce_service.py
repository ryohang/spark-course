from demo.loader import loadCSVfrom
from pyspark.sql import DataFrame
from pyspark.sql import functions as func

def aggregateBlocks(df:DataFrame):
	df.agg(func.sum("blockCount")).show()


df = loadCSVfrom(file_path="/Users/ryo/Downloads/btc.csv")
print("total record count: %d" % (df.count()))

aggregateBlocks(df)