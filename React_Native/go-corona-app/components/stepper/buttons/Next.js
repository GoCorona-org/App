import React from "react";
import { StyleSheet } from 'react-native';
import { Button } from 'react-native-material-ui'

const Next = ({ goToNextStep, disabled }) => (
  <Button
    raised
    primary
    disabled={disabled || false}
    text="Next"
    upperCase={false}
    style={{
      container: disabled === true ?
        [styles.buttonContainer, { backgroundColor: '#C0C0C0' }] : 
        styles.buttonContainer
    }}
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
