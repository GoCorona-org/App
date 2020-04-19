import { Ionicons } from "@expo/vector-icons";
import * as WebBrowser from "expo-web-browser";
import * as React from "react";
import { StyleSheet, Text, View, Modal } from "react-native";
import { useNavigation } from '@react-navigation/native';
import DateRangePicker from "../../components/DateRangePicker";
import {
  RectButton,
  ScrollView,
  TouchableOpacity,
  FlatList,
} from "react-native-gesture-handler";
import { CalendarList } from "react-native-calendars";
import QuarantineDates from "./QuarantineDates"

export default function QuarantineScreen({ defaultSelectedDays = [
  { start: "2020-04-12", end: "2020-04-19", key: "abcd1234", deletable: false },
] }) {

  const [selectedDays, setSelectedDays] = React.useState(defaultSelectedDays);

  const CALENDAR = "CALENDAR";
  const DATE_SELECTOR = "DATE_SELECTOR";

  const navigation = useNavigation()

  const getQuarantineMarkedDays = (startDate, endDate, markedDates) => {
    let deltaDays =
      (endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24);
    let start = new Date(startDate);
    for (var i = 0; i <= deltaDays; i++) {
      start.setDate(startDate.getDate() + i);
      markedDates[formatDate(start)] = {
        marked: true
      };
    }
    return markedDates;
  };


  const formatDate = (date, type) => {
    let dt = zeroPadNumber(date.getDate())
    let month = zeroPadNumber(date.getMonth() + 1)

    if (type == "IN")
      return (
        "" + dt + "-" + month + "-" + date.getFullYear()
      );
    else
      return date.getFullYear() + "-" + month + "-" + dt;
  };

  const zeroPadNumber = (number) => {
    return number < 10 ? "0" + number : "" + number;
  }

  const getMarkedDaysObject = (selectedDays) => {
    let markedDates = {};
    selectedDays.forEach(({ start, end }) => {
      let startDate = new Date(Date.parse(start));
      let endDate = new Date(Date.parse(end));
      markedDates = getQuarantineMarkedDays(startDate, endDate, markedDates);
    });
    return markedDates;
  }

  let markedDays = getMarkedDaysObject(selectedDays);

  return (
    <View
      style={styles.container}
      contentContainerStyle={styles.contentContainer}
    >
      <ScreenHeader
        label="MY CALENDAR"
        iconName="md-add"
        onClick={() => navigation.navigate("QuarantineDates", { selectedDays })}
      />
      <Divider marginTop={12} />
      <View
        style={{
          marginTop: 20,
          flexDirection: "row",
          justifyContent: "center",
          flexWrap: "wrap",
        }}
      >
        <OptionButton label="Quarantine" />
      </View>
      <View style={{ marginTop: 14 }}>
        <CalendarList
          pastScrollRange={0}
          futureScrollRange={4}
          current={Date()}
          scrollEnabled={true}
          minDate={Date()}
          dayComponent={({ marking, date, state }) => {
            return (
              <View
                style={{
                  borderWidth: 1,
                  borderRadius: 4,
                  borderColor: "#818181",
                  backgroundColor: marking instanceof Array ? "transparent" : "#FFCEDF",
                  opacity: state === "disabled" ? 0.4 : 1,
                  width: 42,
                  height: 42,
                  justifyContent: "center",
                }}
              >
                <Text
                  style={{
                    textAlign: "center",
                    color: "#818181",
                    fontSize: 16,
                  }}
                >
                  {date.day}
                </Text>
              </View>
            );
          }}
          markedDates={markedDays}
        />
      </View>
    </View>
  );
}

function ScreenHeader({ label, iconName, onClick }) {
  return (
    <TouchableOpacity onPress={onClick}>
      <View style={styles.headerContainer}>
        <Text style={styles.headerText}>{label}</Text>
        <Ionicons
          name={iconName}
          size={28}
          color="gray"
          style={{ marginLeft: 8 }}
        />
      </View>
    </TouchableOpacity>
  );
}

function Divider({ marginTop }) {
  return (
    <View
      style={{
        backgroundColor: "gray",
        height: 1,
        marginTop: marginTop,
      }}
    />
  );
}

function OptionButton({ label, onPress, backgroundColor }) {
  return (
    <RectButton style={styles.optionButtonStyle} onPress={onPress}>
      <Text style={styles.optionButtonTextStyle}>{label}</Text>
    </RectButton>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#ffffff",
    height: "100%",
    flexDirection: "column",
    justifyContent: "flex-start",
    paddingTop: 10
  },
  contentContainer: {
    paddingTop: 15,
  },
  headerContainer: {
    flexDirection: "row",
    justifyContent: "center",
    alignItems: "center",
  },
  headerText: {
    fontSize: 18,
    color: "#1C1C1C",
    fontWeight: "bold",
  },
  optionButtonStyle: {
    backgroundColor: "#FFCEDF",
    borderColor: "#909090",
    borderWidth: 1,
    borderRadius: 8,
    height: 44,
    justifyContent: "center",
    width: 150,
  },
  optionButtonTextStyle: {
    fontSize: 18,
    color: "black",
    textAlign: "center",
    textAlignVertical: "center",
  },
});
