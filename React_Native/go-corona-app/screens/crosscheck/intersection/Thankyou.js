import React from 'react';
import { StyleSheet, Text, View } from 'react-native';

const Thankyou = (props) => {
  return (
    <View style={styles.container}>
      <Text style={styles.title}>Thank you for sharing your data. This will help our nation trace COVID-19 cases more effectively. </Text>
      <Text style={styles.thankyouNote}>
        {
          `While we process your data, please come back after sometime and use the option in the home screen to check if you are exposed to COVID-19. 
        `
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