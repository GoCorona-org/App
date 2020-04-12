import { Ionicons } from '@expo/vector-icons';
import * as WebBrowser from 'expo-web-browser';
import * as React from 'react';
import { StyleSheet, Text, View, Image } from 'react-native';
import { RectButton } from 'react-native-gesture-handler';
import TermsImage from '../../../assets/images/IntersectionTerms.svg'


export default function IntersectionTerms() {
  return (
    <View style={styles.container}>
      <TermsImage style={styles.image} width="200" height="200" />
      <View style={styles.introduction}>
        <Text style={styles.title}>Terms of Service</Text>
        <Text style={styles.description}>
          Before using the intersection calculator, please read Terms of Service. 
          Remember that: {'\n\n'}
          - <Text style={styles.boldText}>Intersects does not mean virus transfer.</Text> Crossing paths with a COVID +ve does not mean you have been infected. {'\n'}
          - <Text style={styles.boldText}>The results are probabilistic.</Text> Even if you have come in contact with a COVID +ve person, this is only a probabilistic model to know if you have been infected. {'\n'}
          - <Text style={styles.boldText}>Your data is safe.</Text> Information that you provide is anonymous and not shared with anyone.{'\n'}
        </Text>
        <Text> I read and accept Terms of Service and Privacy Policy</Text>
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
    marginTop: 30,
    marginBottom: 15,
    alignSelf: "center"
  },
  introduction: {
    margin: 35,
  },
  title: {
    fontSize: 18,
    fontWeight: "bold",
    marginTop: 1,
  },
  boldText: {
    fontWeight: "bold"
  },
  description: {
    fontSize: 14,
    marginTop: 10,
    lineHeight: 25
  }
});
