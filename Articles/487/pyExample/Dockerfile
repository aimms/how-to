# Pull a pre-built alpine docker image with nginx and python3 installed
FROM tiangolo/uwsgi-nginx-flask:python3.8

ENV LISTEN_PORT=8000
EXPOSE 8000

COPY /app /app

# Uncomment to install additional requirements from a requirements.txt file
# add the libraries you used in your python model to the requirements.txt file and uncomment below code before building the docker image
COPY requirements.txt /
RUN pip install --no-cache-dir -U pip
RUN pip install --no-cache-dir -r /requirements.txt