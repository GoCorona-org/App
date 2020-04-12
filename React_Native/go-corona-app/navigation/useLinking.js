import { useLinking } from '@react-navigation/native';
import { Linking } from 'expo';

export default function(containerRef) {
  return useLinking(containerRef, {
    prefixes: [Linking.makeUrl('/')],
    config: {
      Login: {
        path: 'login',
        screens: {
          Login: 'Login'
        }
      },
      Root: {
        path: 'root',
        screens: {
          Home: 'Home',
          Quarantine: 'Quarantine',
          Checkup: 'Checkup',
          Info: 'Info'
        },
      },
    },
  });
}
