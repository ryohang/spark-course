import os
import sys

from demo.loader import loadCSVfrom
from pyspark.sql import DataFrame
from pyspark.sql import functions as func



def aggregateBlocks(df:DataFrame):
	df.agg(func.sum("blockCount")).show()

ROOT_DIR = os.path.split(os.environ['VIRTUAL_ENV'])[0]
df = loadCSVfrom(file_path=ROOT_DIR+"/data/btc.csv")
print("total record count: %d" % (df.count()))

aggregateBlocks(df)