import { Ionicons } from '@expo/vector-icons';
import * as WebBrowser from 'expo-web-browser';
import React, { useState, useEffect } from 'react';
import { StyleSheet, Text, View, Image, ScrollView } from 'react-native';
import { RectButton } from 'react-native-gesture-handler';
import { CheckBox } from 'react-native-elements'

import TermsImage from '../../../assets/images/IntersectionTerms.svg'

export default function IntersectionTerms({ questions, setValues }) {
  const [agree, setAgree] = useState(false)

  useEffect(() => {
    var values = { name: questions[0].name, value: agree }
    setValues([values])
  }, [agree])

  return (
    <ScrollView>
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
        </View>
        <View style={styles.agreeContainer}>
          <CheckBox containerStyle={{ borderWidth: 0 }} title="I read and accept the Terms of Service and Privay Policy" value="agree" checked={agree} onPress={() => setAgree(!agree)} />
        </View>
      </View>
    </ScrollView>
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
    margin: "5%",
    marginBottom: 0,
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
  },
  agreeContainer: {
    marginLeft: 10,
    marginRight: 10
  }
});
