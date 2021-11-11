from flask import Flask
from flask import request
import kmeansClust

app = Flask(__name__)

@app.route('/hello', methods=["GET"])

def hello_world():
    return "hello world"

@app.route('/', methods=["POST"])

def aimms_call():
    ourInput = request.get_json()
    return kmeansClust.mykMeans(ourInput)

if __name__ == '__main__':
  app.run(host='0.0.0.0', port=8000)
