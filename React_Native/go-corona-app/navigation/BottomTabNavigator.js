import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import * as React from 'react';
import { View, Text } from 'react-native';

import TabBarIcon from '../components/TabBarIcon';
import HomeScreen from '../screens/HomeScreen';
import QuarantineScreen from '../screens/QuarantineScreen';
import CheckupScreen from '../screens/CheckupScreen';
import InfoScreen from '../screens/InfoScreen';

const BottomTab = createBottomTabNavigator();
const INITIAL_ROUTE_NAME = 'Home';

export default function BottomTabNavigator({ navigation, route }) {
  // Set the header title on the parent stack navigator depending on the
  // currently active tab. Learn more in the documentation:
  // https://reactnavigation.org/docs/en/screen-options-resolution.html
  
  navigation.setOptions({ headerTitle: getHeaderTitle(route) });

  return (
    <BottomTab.Navigator initialRouteName={INITIAL_ROUTE_NAME}>
      <BottomTab.Screen
        name="Home"
        component={HomeScreen}
        options={{
          title: 'Spot Corona',
          tabBarIcon: ({ focused }) => <TabBarIcon focused={focused} name="md-home" />,
        }}
      />
      <BottomTab.Screen
        name="Quarantine"
        component={QuarantineScreen}
        options={{
          title: 'Quarantine',
          tabBarIcon: ({ focused }) => <TabBarIcon focused={focused} name="md-lock" />,
        }}
      />
      <BottomTab.Screen
        name="Checkup"
        component={CheckupScreen}
        options={{
          title: 'Checkup',
          tabBarIcon: ({ focused }) => <TabBarIcon focused={focused} name="md-add-circle-outline" />,
        }}
      />
      <BottomTab.Screen
        name="Info"
        component={InfoScreen}
        options={{
          title: 'Info',
          tabBarIcon: ({ focused }) => <TabBarIcon focused={focused} name="md-information-circle-outline" />,
        }}
      />
    </BottomTab.Navigator>
  );
}

function getHeaderTitle(route) {
  const routeName = route.state?.routes[route.state.index]?.name ?? INITIAL_ROUTE_NAME;

  switch (routeName) {
    case 'Home':
      return 'Spot Corona';
    case 'Quarantine':
      return 'Quarantine';
    case 'Checkup':
      return 'Checkup';
    case 'Info':
      return 'Info';
  }
}
