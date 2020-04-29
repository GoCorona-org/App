import queryString from 'query-string';
import * as Google from 'expo-google-app-auth';
import uuid from 'react-native-uuid'
import ClientSecrets from './client_secret.json';
import { setItem, getItem } from './Storage';

export async function generateUUID() {
  return uuid.v4()
}

export async function generateAllUUIDs() {
  const UUIDs = await getItem("UUIDs")

  if (!UUIDs) {
    const userUUID = await generateUUID()
    const medicalUUID = await generateUUID()
    const locationUUID = await generateUUID()

    const UUIDs = { userUUID, medicalUUID, locationUUID }
    console.log('generated new UUIDs:', UUIDs)
    await setItem("UUIDs", UUIDs)

    return UUIDs
  }

  console.log('retrieving existing UUIDs:', UUIDs)
  return UUIDs
}

export async function signInWithGoogleAsync() {
  try {
    const result = await Google.logInAsync({
      ...ClientSecrets,
      scopes: ['profile', 'email'],
    });

    if (result.type === 'success') {
      // generate 3 unique UUIDs
      await generateAllUUIDs()

      return result;
    }
    return { cancelled: true };
  } catch (e) {
    return { error: e };
  }
}

export async function logoutOfGoogleAsync(accessToken) {
  const result = await Google.logOutAsync({ accessToken, ...ClientSecrets });
  return result;
}

export async function getNewAccessTokenAsync(refreshToken) {
  try {
    console.log({
      client_id: ClientSecrets.iosClientId,
      client_secret: ClientSecrets.clientSecret,
      refresh_token: refreshToken,
      grant_type: 'refresh_token',
    });

    const tokenInfo = await fetch('https://www.googleapis.com/oauth2/v4/token', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded',
      },
      body: queryString.stringify({
        client_id: ClientSecrets.iosClientId,
        client_secret: ClientSecrets.clientSecret,
        refresh_token: refreshToken,
        grant_type: 'refresh_token',
      }),
    });

    console.log(await tokenInfo.json());

    return tokenInfo;

    // const result = fetch(`https://accounts.google.com/o/oauth2/v2/token?`)
  } catch (e) {
    return { error: e };
  }
}