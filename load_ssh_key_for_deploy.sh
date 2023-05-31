#!/bin/bash

# create .ssh folder
mkdir -p ~/.ssh

# create private key from Gitlab CI/CD variable
echo "$SSH_PRIVATE_KEY" | tr -d '\r' > ~/.ssh/id_ed25519

# change private key rights
chmod 600 ~/.ssh/id_ed25519

# evaluate ssh-agent (opening ssh-agent)
eval "$(ssh-agent -s)"

# load ssh key into ssh-agent
ssh-add ~/.ssh/id_ed25519

# scan for host key and store into known_hosts file
ssh-keyscan -H 'data.aimms.com' >> ~/.ssh/known_hosts