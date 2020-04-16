import React, { useRef, useState, useEffect } from 'react';
import { Platform, StatusBar, StyleSheet, View } from 'react-native';
import { SplashScreen } from 'expo';
import * as Font from 'expo-font';
import { Ionicons } from '@expo/vector-icons';
import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';

import { getItem } from './utils/Storage';
import { byPassLogin } from './constants/DevSettings';

import RootView from './navigation/BottomTabNavigator';
import useLinking from './navigation/useLinking';
import LoginScreen from './screens/LoginScreen';
import CheckupQuestionnaire from "./screens/checkup/index";
import TravelQuestionnaire from "./screens/crosscheck/travel/Questionnaire";
import IntersectionCalculator from './screens/crosscheck/intersection/index';

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
          <AppStack.Navigator initialRouteName={byPassLogin ? "Root" : "Login"}>
            <AppStack.Screen name="Login" component={LoginScreen} />
            <AppStack.Screen name="Root" component={RootView} />
            <AppStack.Screen name="CheckupQuestionnaire" options={{ title: "Checkup Questionnaire" }} component={CheckupQuestionnaire} />
            <AppStack.Screen name="TravelQuestionnaire" options={{ title: "Travel Questionnaire"}} component={TravelQuestionnaire} />
            <AppStack.Screen name="IntersectionCalculator" options={{ title: "Intersection Calculator"}} component={IntersectionCalculator} />
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
