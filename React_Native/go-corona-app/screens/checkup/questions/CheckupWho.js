import React , { useState } from 'react';
import { StyleSheet, Text, View, Image, ScrollView } from 'react-native';
import {  RadioButton } from 'react-native-paper';
import CustomButton from '../../../components/button/index'


export default function CheckupWho() {
    const [valueNext, setValueNext] = useState('s')
    
    return (
        <ScrollView>
            <View style={styles.viewContainer}>
                <View style={styles.container}>
                    <View style={styles.sectionContainer}>
                        <Text style={styles.title}>Tell us who this checkup is for?</Text>
                        <View  style={styles.agreeContainer}>
        <RadioButton.Group
        onValueChange={valueNext => setValueNext(valueNext)}
        value={valueNext}
         >
        <View style={styles.agreeContainer}>
        <View style={styles.radAlign}>
         <RadioButton.Android value="s" color="#E03D51" uncheckedColor="#D2D2D2" />
         <Text style={styles.radTxt}>Self</Text>
         </View>
        
        </View>
        <View  style={styles.agreeContainer}>
          <View style={styles.radAlign}>
          <RadioButton.Android value="se" color="#E03D51" uncheckedColor="#D2D2D2" />
          <Text style={styles.radTxt}>Someone else and you are assisting them</Text>
          </View>
         
        </View>
      </RadioButton.Group>
        
      </View>
                    </View>
          
                    <View style={styles.sectionContainer}>
                        <Text style={styles.title}>Or, if you dont have any symptoms</Text>
                        <View style={styles.helpLinks}>
                            <CustomButton label="Explore"
                            />
                        </View>
                    </View>
                </View>
            </View>
        </ScrollView>
    );
}

const styles = StyleSheet.create({
    viewContainer: {
        flex: 1,
        backgroundColor: '#fafafa',
        justifyContent: 'flex-start'
    },
    container: {
        justifyContent: 'center'
    },
    sectionContainer: {
        marginTop: 40,
        marginBottom: 40,
        marginLeft:20
        
    },
    title: {
        fontSize: 18,
        fontWeight: "bold",
        alignSelf: 'flex-start',
        marginLeft: 20,
        marginBottom:30
    },
    helpLinks: {
        marginTop: 30,
        padding: 5,
        width: "90%"
    },
    agreeContainer: {
        marginLeft: 8,
        marginRight: 10,
        flexDirection:'column'
      },
      radAlign:{
        flexDirection:'row',
        marginBottom:10
      },
      radTxt:{
        marginTop:8
      }

});
