package model.effect

class UnitEffectTest extends munit.FunSuite:

  test("MoraleBoost is a UnitEffect") {
    val effect: UnitEffect = MoraleBoost
    assert(effect.isInstanceOf[UnitEffect])
  }

  test("CloseBond is a UnitEffect") {
    val effect: UnitEffect = CloseBond
    assert(effect.isInstanceOf[UnitEffect])
  }

  test("MoraleBoost and CloseBond are different") {
    assertNotEquals(MoraleBoost: Any, CloseBond)
  }

  test("MoraleBoost is a singleton") {
    val effect1 = MoraleBoost
    val effect2 = MoraleBoost
    assert(effect1 eq effect2) // Reference equality
  }

  test("CloseBond is a singleton") {
    val effect1 = CloseBond
    val effect2 = CloseBond
    assert(effect1 eq effect2)
  }

  test("can assign effects to UnitEffect trait") {
    val effects: List[UnitEffect] = List(MoraleBoost, CloseBond)
    assertEquals(effects.size, 2)
  }