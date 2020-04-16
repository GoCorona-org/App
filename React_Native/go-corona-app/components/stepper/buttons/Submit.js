import React from "react";
import { StyleSheet, Alert } from "react-native";
import { Button } from 'react-native-material-ui'

const Submit = ({ onSubmit }) => (
  <Button raised primary
    text="Submit"
    upperCase={false}
    onPress={onSubmit}
    style={{ container: styles.buttonContainer }}
  />
)

export default Submit

const styles = StyleSheet.create({
  buttonContainer: {
    width: 100,
    backgroundColor: '#E03D51',
    borderRadius: 5
  }
})