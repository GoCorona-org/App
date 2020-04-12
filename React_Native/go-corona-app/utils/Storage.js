import { AsyncStorage } from 'react-native';

export async function setItem(key, value) {
  try {
    await AsyncStorage.setItem(key, JSON.stringify(value));
    return true;
  } catch (error) {
    // Error saving data
    return false;
  }
}

export async function getItem(key) {
  try {
    const value = await AsyncStorage.getItem(key);
    return JSON.parse(value);
  } catch (error) {
    // Error retrieving data
    return null;
  }
}

export async function removeItem(key) {
  try {
    const remove = await AsyncStorage.removeItem(key);

    return remove;
  } catch (error) {
    return null;
  }
}

export async function clear() {
  try {
    const allKeys = await AsyncStorage.getAllKeys();
    const clearAll = await AsyncStorage.multiRemove(allKeys);

    return clearAll;
  } catch (error) {
    return null;
  }
}