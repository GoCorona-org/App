import { Ionicons } from '@expo/vector-icons';
import * as WebBrowser from 'expo-web-browser';
import * as React from 'react';
import { StyleSheet, Text, View, Button } from 'react-native';
import { RectButton, ScrollView } from 'react-native-gesture-handler';
import { useNavigation } from '@react-navigation/native';

import Separator from '../../components/Separator';
import TravelImage from '../../assets/images/Travel.svg'
import IntersectionImage from '../../assets/images/IntersectionCalculator.svg'
import CustomButton from '../../components/button';

export default function CrosscheckScreen() {
  const navigation = useNavigation();

  return (
    <View style={styles.container}>
      <CheckupType
        title="Travel based cross-check for COVID-19"
        Image={TravelImage}
        buttonTitle="Start questionnaire"
        onPress={() => navigation.navigate('TravelQuestionnaire')}
      />
      <Separator />
      <CheckupType
        title="Know if you have met a COVID +ve person"
        Image={IntersectionImage}
        buttonTitle="Open intersection calculator"
        onPress={() => navigation.navigate('IntersectionCalculator')}
      />
    </View>
  );
}

function CheckupType({ title, Image, buttonTitle, onPress }) {
  return (
    <View style={styles.checkupTypeContainer}>
      <Text style={styles.checkupTypeTitle}>{title}</Text>
      <Image style={styles.checkupTypeImage} width="200" height="100" />
      <CustomButton label={buttonTitle} onPress={onPress} />
    </View>
  )
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fafafa',
    justifyContent: 'space-evenly',
  },
  checkupTypeContainer: {
    alignItems: 'center',
    justifyContent: 'flex-start'
  },
  checkupTypeTitle: {
    fontSize: 18,
    fontWeight: "bold",
  },
  checkupTypeImage: {
    marginTop: 30,
    marginBottom: 15,
  }
});
