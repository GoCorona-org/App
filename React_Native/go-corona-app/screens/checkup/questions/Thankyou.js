import React from 'react';
import { StyleSheet, Text, View } from 'react-native';

const Thankyou = (props) => {
  return (<View style={styles.container}>
    <Text style={styles.title}>Thank you</Text>
    <Text style={styles.thankyouNote}>
      {
        `For Taking Timeout to provide the answers to this survey. Before hitting the submit button, please verify that all the responses are answered best to your abilities. Your honest responses are extremely valuable to us as they do not allow us to understand your symptoms and provide you with the next actionable steps but will help us track the spread of COVID-19 in your area
        `
      }
    </Text>
  </View>)
}

const styles = StyleSheet.create({
  title: {
    fontSize: 16,
    fontWeight: "bold"
  },
  thankyouNote: {
    paddingTop: 20,
    fontWeight: "bold",
    fontSize: 14
  },
  container: {
    flex: 1,
    padding: 30,
  }
})

export default Thankyou