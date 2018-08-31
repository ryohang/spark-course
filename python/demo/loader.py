from .spark_config import sparkSession

def loadCSVfrom(file_path:str):
  df = sparkSession.read.csv(file_path,header=True,sep=",")
  return df 