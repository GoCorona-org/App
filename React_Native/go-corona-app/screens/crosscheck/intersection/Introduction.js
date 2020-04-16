import { Ionicons } from '@expo/vector-icons';
import * as WebBrowser from 'expo-web-browser';
import * as React from 'react';
import { StyleSheet, Text, View, Image, ScrollView } from 'react-native';
import { RectButton } from 'react-native-gesture-handler';

import ProgressBarContainer from '../../../components/ProgressBar';
import IntroductionImage from '../../../assets/images/IntersectionIntroduction.svg'


export default function CrosscheckIntrodctionScreen() {
  const text = "Patient"
  const pageNo = 1;

  return (
    <ScrollView>
      <View style={styles.container}>
        <ProgressBarContainer textOnTop={text} currPage={pageNo} totalPages={3} />
        <IntroductionImage style={styles.image} width="200" height="200" />
        <View style={styles.introduction}>
          <Text style={styles.title}>Hello!</Text>
          <Text style={styles.description}>
            Intersection calculator helps us understand if you have
            crossed paths with a COVID positive person. It could have been a shopkeeper,
            a person who stood next to you on a local train/bus or even a cab driver.
            This increases the probablity of you getting infected.
            Let us know the locations you have been to the last 30 days using Google maps.
            Go through our tutorial on how to downlaod your location history from Google and upload it back here on our calculator.
        </Text>
        </View>
      </View>
    </ScrollView>
  );
}

function OptionButton({ icon, label, onPress, isLastOption }) {
  return (
    <RectButton style={[styles.option, isLastOption && styles.lastOption]} onPress={onPress}>
      <View style={{ flexDirection: 'row' }}>
        <View style={styles.optionIconContainer}>
          <Ionicons name={icon} size={22} color="rgba(0,0,0,0.35)" />
        </View>
        <View>
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
    justifyContent: 'flex-start'
  },
  image: {
    marginTop: 10,
    marginBottom: 15,
    alignSelf: "center"
  },
  introduction: {
    margin: 40,
  },
  title: {
    fontSize: 18,
    fontWeight: "bold",
    marginTop: 1,
  },
  description: {
    fontSize: 16,
    marginTop: 10,
    lineHeight: 30
  }
});
