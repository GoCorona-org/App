import { Ionicons } from '@expo/vector-icons';
import * as WebBrowser from 'expo-web-browser';
import React from 'react';
import { StyleSheet, Text, View } from 'react-native';
import { RectButton, ScrollView } from 'react-native-gesture-handler';
import { useNavigation } from '@react-navigation/native';
import Constants from 'expo-constants';

import { logoutOfGoogleAsync } from '../utils/Login';
import { getItem, removeItem } from '../utils/Storage';
import { byPassLogin, byPassGoogleLogin } from '../constants/DevSettings';

export default function LinksScreen() {
  const navigation = useNavigation()

  const handleLogoutPress = async () => {
    const loggedInuser = await getItem("login")
    await logoutOfGoogleAsync(loggedInuser.accessToken)
    await removeItem("login")
    console.log('user logged out successfully!')

    navigation.replace(byPassGoogleLogin ? 'Login' : 'GLogin')
  }

  return (
    <View style={styles.container}>
      <ScrollView>
        <OptionButton
          icon="md-school"
          label="Helpline"
          onPress={() => WebBrowser.openBrowserAsync('https://docs.expo.io')}
        />

        <OptionButton
          icon="ios-chatboxes"
          label="FAQs"
          onPress={() => WebBrowser.openBrowserAsync('https://forums.expo.io')}
        />

        {
          byPassLogin ?
            null :
            <OptionButton
              icon="ios-log-out"
              label="Logout"
              onPress={handleLogoutPress}
              isLastOption
            />
        }

      </ScrollView>
      <View style={styles.appVersion}>
        <Text style={styles.appVersionText}>{`Version ${Constants.manifest.version}`}</Text>
      </View>
    </View>
  );
}

function OptionButton({ icon, label, onPress, isLastOption }) {
  return (
    <RectButton style={[styles.option, isLastOption && styles.lastOption]} onPress={onPress}>
      <View style={{ flexDirection: 'row' }}>
        <View style={styles.optionIconContainer}>
          <Ionicons name={icon} size={22} color="rgba(0,0,0,0.35)" />
        </View>
        <View style={styles.optionTextContainer}>
          <Text style={styles.optionText}>{label}</Text>
        </View>
      </View>
    </RectButton>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fafafa',
  },
  contentContainer: {
    paddingTop: 15,
  },
  optionIconContainer: {
    marginRight: 12,
  },
  option: {
    backgroundColor: '#fdfdfd',
    paddingHorizontal: 15,
    paddingVertical: 15,
    borderWidth: StyleSheet.hairlineWidth,
    borderBottomWidth: 0,
    borderColor: '#ededed',
  },
  lastOption: {
    borderBottomWidth: StyleSheet.hairlineWidth,
  },
  optionText: {
    fontSize: 15,
    alignSelf: 'flex-start',
    marginTop: 1,
  },
  appVersion: {
    height: 50,
    alignItems: "center",
    justifyContent: "center"
  },
  appVersionText: {
    fontSize: 15,
  }
});
