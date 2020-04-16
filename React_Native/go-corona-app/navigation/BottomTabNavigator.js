import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import * as React from 'react';
import { View, Text } from 'react-native';

import TabBarIcon, {MaterialTabBarIcon} from '../components/TabBarIcon';
import HomeScreen from '../screens/HomeScreen';
import QuarantineScreen from '../screens/QuarantineScreen';
import CheckupScreen from '../screens/checkup/index';
import CrosscheckScreen from '../screens/crosscheck/CrosscheckScreen';
import InfoScreen from '../screens/InfoScreen';
import Icon from '../components/icon/Icon';

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
          tabBarIcon: ({ focused }) => <Icon name="home" size={25} focused={focused} />,
        }}
      />
      <BottomTab.Screen
        name="Quarantine"
        component={QuarantineScreen}
        options={{
          title: 'Quarantine',
          tabBarIcon: ({ focused }) => <MaterialTabBarIcon name="do-not-disturb"  focused={focused} />,
        }}
      />
      <BottomTab.Screen
        name="Checkup"
        component={CheckupScreen}
        options={{
          title: 'Checkup',
          tabBarIcon: ({ focused }) => <Icon name="checkup" size={25} focused={focused} />,
        }}
      />
      <BottomTab.Screen
        name="Crosscheck"
        component={CrosscheckScreen}
        options={{
          title: 'Cross-check',
          tabBarIcon: ({ focused }) => <Icon name="crosscheck" size={25} focused={focused} />,
        }}
      />
      <BottomTab.Screen
        name="Info"
        component={InfoScreen}
        options={{
          title: 'Info',
          tabBarIcon: ({ focused }) => <TabBarIcon name="md-information-circle-outline" focused={focused} />,
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
    case 'Crosscheck':
      return 'Crosscheck';
    case 'Info':
      return 'Info';
  }
}
