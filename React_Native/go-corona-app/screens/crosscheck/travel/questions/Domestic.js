import React, { Component, useEffect } from "react";
import { StyleSheet, View, Text, Image } from "react-native";
import { ScrollView, RectButton } from "react-native-gesture-handler";
import { Divider } from "react-native-material-ui";
import { RadioButton } from "react-native-paper";
import { Input } from "react-native-elements";
import Autocomplete from "react-native-autocomplete-input";
import { KeyboardAwareScrollView } from 'react-native-keyboard-aware-scrollview';

import DomesticTravelImage from "../../../../assets/images/DomesticTravel.svg";

export default function Domestic({ questions, setValues }) {
  const [yesSelected, setYesSelected] = React.useState(false)
  const [fromCity, setFromCity] = React.useState("")
  const [toCity, setToCity] = React.useState("")

  useEffect(() => {
    const name = questions[0].name;
    setValues([{ name, value: yesSelected }])
  }, [yesSelected])

  useEffect(() => {
    const name = questions[1].name;
    setValues([{ name, value: fromCity }])
  }, [fromCity])

  useEffect(() => {
    const name = questions[2].name;
    setValues([{ name, value: toCity }])
  }, [toCity])

  return (
    <KeyboardAwareScrollView style={styles.containerStyle}>
      <View style={{ flexDirection: "column" }}>
        <Text style={styles.headerQuestTextStyle}>
          What about domestic travel ?
        </Text>
        <DomesticTravelImage
          width="250"
          height="200"
          style={{ alignSelf: "center" }}
        />
        <Text style={styles.subQuestionStyle}>
          Have you travelled anywhere inside India by flight in the last 30
          days?
        </Text>
        <View style={{ marginTop: 14 }}>
          <Divider />
        </View>
        <RadioButton.Group>
          <View>
            <View style={styles.radAlign}>
              <RadioButton.Android
                onPress={() => {
                  setYesSelected(false);
                }}
                value={"No"}
                status={yesSelected === false ? "checked" : "unchecked"}
                color="#E03D51"
                uncheckedColor="#D2D2D2"
              />
              <Text style={{ textAlign: "center" }}>No</Text>
            </View>
          </View>
          <Divider />
          <View style={styles.radAlign}>
            <RadioButton.Android
              onPress={() => {
                setYesSelected(true);
              }}
              value="Yes"
              color="#E03D51"
              uncheckedColor="#D2D2D2"
              status={yesSelected === true ? "checked" : "unchecked"}
            />
            <Text>Yes</Text>
          </View>
        </RadioButton.Group>
        <View style={{ opacity: yesSelected === true ? 1 : 0 }}>
          <Divider />
          <View>
            <Text style={styles.subQuestionStyle}>
              If yes, then select the airports you were at
          </Text>
          </View>

          <View style={{ marginTop: 10 }}>
            <Input placeholder="From" onChangeText={setFromCity} />
          </View>
          <View style={{ marginTop: 10, marginBottom: 20 }}>
            <Input placeholder="To" onChangeText={setToCity} />
          </View>
        </View>

      </View>
    </KeyboardAwareScrollView>
  );
}

const styles = StyleSheet.create({
  containerStyle: {
    paddingLeft: "5%",
    paddingRight: "5%",
  },

  headerQuestTextStyle: {
    fontWeight: "bold",
    padding: "5%",
  },

  subQuestionStyle: {
    fontSize: 14,
    margin: 20,
    textAlign: "center",
  },
  radAlign: {
    flexDirection: "row",
    alignItems: "center"
  },
  radioButtonStyle: {
    paddingLeft: 20,
  },
});
