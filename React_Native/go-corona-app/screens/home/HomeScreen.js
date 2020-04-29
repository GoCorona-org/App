import { MaterialCommunityIcons } from '@expo/vector-icons';
import * as Location from 'expo-location';
import React, { Component, useState, useEffect, useRef } from 'react';
import { ActivityIndicator, StyleSheet, Text, TouchableOpacity, View } from 'react-native';
import MapView, { PROVIDER_GOOGLE } from 'react-native-maps';
import { useNavigation } from '@react-navigation/native';

import Button from '../../components/button/Button';
import GooglePlacesInput from '../../components/map/Places';
import Point from '../../components/map/Point';
import Popup, { PopupStyles } from '../../components/popup/Popup';
import { csoptsApi } from '../../constants/AppSettings';
import Http from '../../services/Http';
import ExposureBtn from './exposure';

// TODO: move to constants
const latitudeDelta = 0.2;
const longitudeDelta = 0.1;

export default function HomeScreen() {
  const [loading, setLoading] = useState(true)
  const [region, setRegion] = useState(null)
  const [points, setPoints] = useState([])
  const [error, setError] = useState(false)
  const [popupVisibility, setPopupVisibility] = useState(true)
  const navigation = useNavigation()
  const map = useRef(null)

  useEffect(() => {
    const init = async () => {
      let { status } = await Location.requestPermissionsAsync();
      if (status !== 'granted') {
        // TODO: move to string constants
        setError('Location permission is needed')
        return;
      }
    }
    init()
  }, [])

  useEffect(() => {
    const update = async () => {
      await updateMapData()
    }
    update()
  }, [])

  // fetch heatmap data everytime we navigate to Map View
  useEffect(() => {
    const focusSubscription = navigation.addListener('focus', async () => {
      await updateMapData()
    });

    return () => focusSubscription && focusSubscription.remove && focusSubscription.remove()
  }, [navigation.isFocused])

  updateMapData = async () => {
    try {
      const location = await Location.getCurrentPositionAsync({});
      const { latitude, longitude } = location.coords;

      const heatMapData = await fetchHeatmapFromApi()
      const points = getHeatMapPoints(heatMapData);

      setHeatMapPoints(latitude, longitude, points);
    } catch (error) {
      console.log("Fetching heatmap failed:", error)
      setLoading(false)
    }
  }

  fetchHeatmapFromApi = async () => {
    const exposed = await Http.get(`${csoptsApi}/exposed`);
    const exposedPoints = exposed.data.map(ed => ({ ...ed, status: 2 }))

    const positive = await Http.get(`${csoptsApi}/positive`);
    const positivePoints = positive.data.map(pd => ({ ...pd, status: 1 }))

    return exposedPoints.concat(positivePoints)
  }

  onMapReady = () => {
    map.current.animateToRegion(region);
  }

  setHeatMapPoints = (latitude, longitude, points) => {
    setRegion({
      latitude,
      longitude,
      latitudeDelta,
      longitudeDelta
    })
    setLoading(false)
    setPoints(points)
  }

  getHeatMapPoints = (data) => {
    return data
  }

  onLocationSelect = (location) => {
    const region = {
      latitude: location.lat,
      longitude: location.lng,
      latitudeDelta,
      longitudeDelta
    }

    setTimeout(() => {
      map.current.animateToRegion(region);
    }, 0);
  }

  togglePopup = () => setPopupVisibility(!popupVisibility)


  goToCurrentPoition = async () => {
    const location = await Location.getCurrentPositionAsync({});
    map.current.animateToRegion({ ...location.coords, latitudeDelta, longitudeDelta });
  }

  renderShowLocationButton = () => {
    return (
      <TouchableOpacity
        style={styles.myLocationButton}
        onPress={() => {
          goToCurrentPoition()
        }}
      >
        <MaterialCommunityIcons name='crosshairs-gps' size={24} />
      </TouchableOpacity>
    )
  }

  renderExposureBtn = () => {
    return (
      <TouchableOpacity
        style={styles.exposureBtn}
        onPress={() => {
          goToCurrentPoition()
        }}
      >
        <MaterialCommunityIcons name='crosshairs-gps' size={24} />
      </TouchableOpacity>
    )
  }

  return (
    <View style={styles.container}>
      {loading ?
        <ActivityIndicator></ActivityIndicator> :
        <>
          <View style={styles.searchInput}>
            <GooglePlacesInput onLocationSelect={onLocationSelect}></GooglePlacesInput>
          </View>
          <MapView
            maxZoomLevel={14}
            showsBuildings={true}
            ref={map}
            provider={PROVIDER_GOOGLE}
            onMapReady={onMapReady}
            showsUserLocation={true}
            showsMyLocationButton={false}
            onRegionChangeComplete={region => setRegion(region)}
            style={[styles.map]}
          >
            {points.map((i, key) =>
              <Point point={i} region={region} key={key}></Point>
            )}
          </MapView>
          {renderShowLocationButton()}
          <ExposureBtn />
          <Popup isVisible={popupVisibility}>
            <Text style={PopupStyles.question}>{"Are you currently suffering from any kind of illness ?"}</Text>
            <Button onPress={() => togglePopup()} label={"I'm not sure"} style={PopupStyles.button1} labelStyle={PopupStyles.buttonlabel1} />
            <Button onPress={() => togglePopup()} label={"Mark myself safe"} style={PopupStyles.button2} labelStyle={PopupStyles.buttonlabel2} />
          </Popup>
        </>
      }
    </View>
  );
};

HomeScreen.navigationOptions = {
  header: null,
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
    alignItems: 'center',
    justifyContent: 'center',
  },
  searchInput: {
    zIndex: 1000,
    maxWidth: 400,
    backgroundColor: '#fff',
    width: '75%',
    position: 'absolute',
    top: 20,
    left: '4%',
    elevation: 3,
    alignItems: 'center',
    justifyContent: 'center',
    borderRadius: 14
  },
  myLocationButton: {
    backgroundColor: '#fff',
    position: 'absolute',
    bottom: 10,
    right: 10,
    padding: 15,
    elevation: 3,
    alignItems: 'center',
    alignSelf: 'flex-end',
    justifyContent: 'center',
    borderRadius: 50
  },
  map: {
    ...StyleSheet.absoluteFillObject,
  },

});

