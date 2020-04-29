import { Ionicons } from '@expo/vector-icons';
import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';
import { SplashScreen } from 'expo';
import * as Font from 'expo-font';
import React, { useEffect, useRef, useState } from 'react';
import { Platform, StatusBar, StyleSheet, View } from 'react-native';
import { byPassLogin, byPassGoogleLogin } from './constants/DevSettings';
import RootView from './navigation/BottomTabNavigator';
import useLinking from './navigation/useLinking';
import IntersectionCalculator from './screens/crosscheck/intersection/index';
import TravelQuestionnaire from "./screens/crosscheck/travel/TravelScreen";
import { Otpscreen } from './screens/login/OtpScreen';
import { Signupscreen } from './screens/login/SignupScreen';
import LoginScreen from './screens/LoginScreen';
import { Loginscreen } from './screens/login/LoginScreen';
import QuarantineDates from './screens/quarantine/QuarantineDates';
import { getItem } from './utils/Storage';

const AppStack = createStackNavigator();

export default function App(props) {
  const [isLoadingComplete, setLoadingComplete] = useState(false);
  const [loggedInUser, setLoggedInUser] = useState(null);
  const [initialNavigationState, setInitialNavigationState] = useState();
  const containerRef = useRef();
  const { getInitialState } = useLinking(containerRef);

  // Load any resources or data that we need prior to rendering the app
  useEffect(() => {
    async function loadResourcesAndDataAsync() {
      try {
        SplashScreen.preventAutoHide();

        // Load our initial navigation state
        setInitialNavigationState(await getInitialState());
        setLoggedInUser(await getItem('login'));

        // Load fonts
        await Font.loadAsync({
          ...Ionicons.font,
          'space-mono': require('./assets/fonts/SpaceMono-Regular.ttf'),
          'spotcorona': require('./assets/fonts/spotcorona.ttf')
        });
      } catch (e) {
        // We might want to provide this error information to an error reporting service
        console.warn(e);
      } finally {
        setLoadingComplete(true);
        SplashScreen.hide();
      }
    }

    loadResourcesAndDataAsync();
  }, []);
  if (!isLoadingComplete && !props.skipLoadingScreen) {
    return null;
  } else {
    return (
      <View style={styles.container}>
        {Platform.OS === 'ios' && <StatusBar barStyle="default" />}
        <NavigationContainer ref={containerRef} initialState={initialNavigationState}>
          <AppStack.Navigator initialRouteName={byPassLogin ? "Root" : byPassGoogleLogin ? "Login" : "GLogin"}>
            {
              byPassGoogleLogin ? <AppStack.Screen options={{ headerShown: false }} name="Login" component={Loginscreen} />
                : <AppStack.Screen options={{ headerShown: false }} name="GLogin" component={LoginScreen} />}
            <AppStack.Screen options={{ headerShown: false }} name="SignUp" component={Signupscreen} />
            <AppStack.Screen options={{ headerShown: false }} name="Otp" component={Otpscreen} />
            <AppStack.Screen name="Root" component={RootView} />
            <AppStack.Screen name="QuarantineDates" options={{ title: "Edit Quarantine Days" }} component={QuarantineDates} />
            <AppStack.Screen name="TravelQuestionnaire" options={{ title: "Travel Questionnaire" }} component={TravelQuestionnaire} />
            <AppStack.Screen name="IntersectionCalculator" options={{ title: "Intersection Calculator" }} component={IntersectionCalculator} />
          </AppStack.Navigator>
        </NavigationContainer>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
  },
});
