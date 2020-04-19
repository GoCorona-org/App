import React, { Component } from "react";
import { StyleSheet, View, Text, Image } from "react-native";
import { ScrollView, RectButton } from "react-native-gesture-handler";
import LockdownImage from "../../../../assets/images/Lockdown.svg";
import { RadioButton, Divider } from "react-native-material-ui";
import { Input, CheckBox } from "react-native-elements";
import Autocomplete from "react-native-autocomplete-input";

export default function Lockdown() {
  const [local,setLocalSelected] = React.useState(false);
  const [auto,setAutoSelected] = React.useState(false);
  const [pv,setPvSelected] = React.useState(false);
  return (
    <ScrollView style={styles.containerStyle}>
      <View style={{ flexDirection: "column" }}>
        <Text style={styles.headerQuestTextStyle}>
          Stranded due to the lockdown?
        </Text>
        <LockdownImage
          width="340"
          height="100"
          style={{ alignSelf: "center", marginTop: 10 }}
        />
        <Text style={styles.subQuestionStyle}>
          Which city are you from and where are you staying now?
        </Text>
        <View style={{ paddingTop: 20 }}>
          <Divider />
        </View>

        <View>
          <Autocomplete placeholder="Hometown" />
        </View>

        <Divider />
        <View style={{ paddingTop: 10 }}>
          <Autocomplete placeholder="Current location" />
        </View>
        <Divider />

        <Text style={styles.subQuestionStyle}>
          What modes of transport have you used for commute in the last 30 days?
        </Text>
        <Divider />
        <CheckBox
          title="Local train or Bus"
          containerStyle={styles.noBorder}
          onPress={()=>{ setLocalSelected(!local)}}
          checked={local}
          checkedColor="#E03D51"
          uncheckedColor="#D2D2D2"
        />
        <Divider />
        <CheckBox
          title="Auto Rickshaw or Cab"
          containerStyle={styles.noBorder}
          checkedColor="#E03D51"
          uncheckedColor="#D2D2D2"
          onPress={()=>{ setAutoSelected(!auto)}}
          checked={auto}
        />
        <Divider />
        <CheckBox
          title="Personal Vehicle"
          containerStyle={styles.noBorder}
          checkedColor="#E03D51"
          uncheckedColor="#D2D2D2"
          onPress={()=>{ setPvSelected(!pv)}}
          checked={pv}
        />
        <Divider />
      </View>
    </ScrollView>
  );
}

const styles = StyleSheet.create({
  containerStyle: {
    paddingLeft: "5%",
    paddingRight: "5%"
  },

  headerQuestTextStyle: {
    fontWeight: "bold",
    padding: "5%",
  },

  subQuestionStyle: {
    fontSize: 14,
    margin: 20,
    textAlign: "center"
  },

  noBorder: {
    borderWidth: 0
    
  },

});
