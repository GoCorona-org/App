import React from "react";
import { StyleSheet } from 'react-native';
import { Button } from 'react-native-material-ui'
import { Ionicons } from '@expo/vector-icons';
import Colors from "../../../constants/Colors";

const Back = ({ goToPreviousStep }) => (
  <Button
    primary
    icon={<Ionicons
      name="ios-arrow-back"
      size={20}
      color={Colors.tabIconSelected}
    />}
    upperCase={false}
    text=" Back"
    style={{ container: styles.buttonContainer, fontSize: 10 }}
    onPress={() => goToPreviousStep()}
  />
)

export default Back

const styles = StyleSheet.create({
  buttonContainer: {
    width: 100,
  },
})