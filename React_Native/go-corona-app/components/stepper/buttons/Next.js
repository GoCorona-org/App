import React from "react";
import { StyleSheet } from 'react-native';
import { Button } from 'react-native-material-ui'

const Next = ({ goToNextStep }) => (
  <Button
    raised
    primary
    text="Next"
    upperCase={false}
    style={{ container: styles.buttonContainer }}
    onPress={() => goToNextStep()}
  />
)

export default Next

const styles = StyleSheet.create({
  buttonContainer: {
    width: 100,
    backgroundColor: '#E03D51',
    borderRadius: 5
  }
})
