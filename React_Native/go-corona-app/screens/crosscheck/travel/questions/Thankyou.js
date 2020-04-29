import React from 'react';
import { StyleSheet, Text, View } from 'react-native';

const Thankyou = (props) => {
  return (
    <View style={styles.container}>
      <Text style={styles.title}>Thank you for taking the time to provide answers to this survey. </Text>
      <Text style={styles.thankyouNote}>
        {
          `Before hitting the submit button, please verify that all the responses are answered best to your abilities. Your honest responses are extremely valuable to us as they not only allow us to understand your symptoms and provide you with the next actionable steps but will also help us track the spread of COVID-19 in your area.`
        }
      </Text>
    </View>
  )
}

const styles = StyleSheet.create({
  title: {
    fontSize: 18,
    fontWeight: "bold"
  },
  thankyouNote: {
    paddingTop: 20,
    fontWeight: "bold",
    fontSize: 18
  },
  container: {
    flex: 1,
    padding: 30,
  }
})

export default Thankyou