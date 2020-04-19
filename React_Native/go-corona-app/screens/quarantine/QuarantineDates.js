import { Ionicons } from "@expo/vector-icons";
import React, { useState } from "react";
import { StyleSheet, Text, View, Modal } from "react-native";
import DateRangePicker from "../../components/DateRangePicker";
import {
    RectButton,
    ScrollView,
    TouchableOpacity,
    FlatList,
} from "react-native-gesture-handler";

import { CalendarList } from "react-native-calendars";

import Separator from '../../components/Separator';
import { makeid } from "../../utils/helpers";

const capitalizeWord = word => word.charAt(0).toUpperCase() + word.slice(1)

function ChooseStartAndEndDate({ type, date, month, onClick, key }) {
    return (
        <TouchableOpacity onPress={onClick} key={key}>
            <View style={{ marginTop: 12 }}>
                <Text style={styles.chooseYourDateTextStyle}>
                    {capitalizeWord(type) + " date"}
                </Text>
                <Separator marginTop={8} />
                <View
                    marginTop={12}
                    paddingLeft={"5%"}
                    style={{ flexDirection: "row" }}
                >
                    <DateChip number={date} />
                    <DateChip marginLeft={12} number={month} />
                </View>
            </View>
        </TouchableOpacity>
    );
}

function DateChip({ number, marginLeft }) {
    return (
        <View
            style={{
                height: 36,
                backgroundColor: "#E9E9E9",
                borderRadius: 8,
                width: 48,
                justifyContent: "center",
                marginLeft: marginLeft,
            }}
        >
            <Text style={{ fontSize: 16, fontWeight: "bold", textAlign: "center" }}>
                {number}
            </Text>
        </View>
    );
}

const canAddMoreQuarantineDates = dates => dates.length < 2

function AddQuarantineDates({
    selected,
    onRemoveClicked,
    onAddClicked,
    onDateRangeSelected,
}) {
    const [showDatePicker, setShowDatePicker] = React.useState(false);

    return (
        <View>
            <Modal visible={showDatePicker} transparent={true}>
                <View style={styles.modalContainerStyle}>
                    <DateRangePicker
                        onSuccess={(se, ed) => {
                            setShowDatePicker(false);
                            onDateRangeSelected(se, ed);
                        }}
                    />
                </View>
            </Modal>

            <Text
                style={{
                    fontWeight: "bold",
                    fontSize: 16,
                    paddingTop: "3%",
                    paddingLeft: "5%",
                    paddingBottom: "3%",
                }}
            >
                {"When in doubt, Self-Quarantine"}
            </Text>
            <View style={{ justifyContent: "space-around", height: "100%" }}>
                <ScrollView>
                    {selected.map(({ start, end, deletable }, index) => {
                        return <AddedQuarantineDateRange
                            key={`q_${index}`}
                            start={start}
                            end={end}
                            deletable={deletable}
                            onDeleteClick={() => onRemoveClicked(index)}
                        />
                    })}
                    {canAddMoreQuarantineDates(selected) ?
                        <AddMoreButton
                            onClick={() => {
                                setShowDatePicker(true);
                            }}
                        /> : null}
                </ScrollView>
            </View>
        </View>
    );
}

function AddMoreButton({ onClick }) {
    return (
        <RectButton
            style={{
                width: 112,
                height: 36,
                backgroundColor: "#A9E7CB",
                borderColor: "#909090",
                borderWidth: 1,
                borderRadius: 8,
                marginTop: 32,
                marginLeft: "5%",
                justifyContent: "center",
            }}
            onPress={onClick}
        >
            <Text
                style={{ textAlign: "center", fontWeight: "bold", color: "#989898" }}
            >
                ADD
      </Text>
        </RectButton>
    );
}

function AddedQuarantineDateRange({ start, end, deletable, onDeleteClick }) {
    return (
        <View>
            <ChooseStartAndEndDate
                type="start"
                month={start.split("-")[1]}
                date={start.split("-")[2]}
            />
            <ChooseStartAndEndDate
                type="end"
                month={end.split("-")[1]}
                date={end.split("-")[2]}
            />
            {deletable ? <DeleteButton onClick={onDeleteClick} /> : null}
        </View>
    );
}

function DeleteButton({ key, onClick }) {
    return (
        <RectButton
            style={{
                width: 112,
                height: 36,
                backgroundColor: "#FFCEDF",
                borderColor: "#909090",
                borderWidth: 1,
                borderRadius: 8,
                marginTop: 12,
                marginLeft: "5%",
                justifyContent: "center",
            }}
            onPress={onClick}
        >
            <Text
                style={{ textAlign: "center", fontWeight: "bold", color: "#989898" }}
            >
                DELETE
      </Text>
        </RectButton>
    );
}

export default function QurantineDates({ route }) {
    const [selectedDays, setSelectedDays] = useState(route.params.selectedDays)

    return (
        <ScrollView style={styles.container} contentContainerStyle={styles.contentContainer}>
            <AddQuarantineDates
                selected={selectedDays}
                onRemoveClicked={(index) => {
                    selectedDays.splice(index, 1);
                    let newSelection = [];
                    selectedDays.forEach((s) => { newSelection.push(s) })
                    setSelectedDays(newSelection);
                }}
                onDateRangeSelected={(sd, ed) => {
                    selectedDays.push({
                        'start': sd,
                        'end': ed,
                        'key': makeid(4),
                        deletable: true
                    })
                    setSelectedDays(selectedDays);
                }}
            />
        </ScrollView>
    )
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#fafafa',
    },
    contentContainer: {
        paddingTop: 15,
        padding: "5%"
    },
    chooseYourDateTextStyle: {
        fontSize: 16,
        paddingLeft: "5%",
    },
    modalContainerStyle: {
        margin: "10%",
        backgroundColor: "white",
        borderRadius: 20,
        padding: 35,
        alignItems: "center",
        shadowColor: "#000",
        shadowOffset: {
            width: 0,
            height: 2,
        },
        shadowOpacity: 0.25,
        shadowRadius: 3.84,
        elevation: 5,
    },
})