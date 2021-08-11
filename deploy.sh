#!/bin/bash
# expects variables to be set:
# - OSSRH_USERNAME
# - OSSRH_PASSWORD
# - GPG_KEY_NAME
# - GPG_PASSPHRASE
# expects file to exist:
# - gpg.key

set -e

# Check the variables are set
if [ -z "$OSSRH_USERNAME" ]; then
  echo "missing environment value: OSSRH_USERNAME" >&2
  exit 1
fi

if [ -z "$OSSRH_PASSWORD" ]; then
  echo "missing environment value: OSSRH_PASSWORD" >&2
  exit 2
fi

if [ -z "$GPG_KEY_NAME" ]; then
  echo "missing environment value: GPG_KEY_NAME" >&2
  exit 3
fi

if [ -z "$GPG_PASSPHRASE" ]; then
  echo "missing environment value: GPG_PASSPHRASE" >&2
  exit 4
fi

# Prepare the local keyring (requires travis to have decrypted the file
# beforehand)
gpg --fast-import gpg.key

# Prepare the m2 settings
cp .travis.settings.xml $HOME/.m2/settings.xml

# Run the maven deploy steps
mvn deploy -P release -DskipTests=true --settings ".travis.settings.xml"
