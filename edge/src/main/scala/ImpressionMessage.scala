/*
 * Copyright (c) 2018. Bionic 8 Analytics Ltd.
 * The Software and accompanying documentation is owned by Bionic 8 Analytics Ltd (Bionic). Bionic reserves all rights in and to the Software and documentation.
 * You may not use, copy, modify, distribute or make any other disposition in the software or documentation without the express written permission and subject to the terms of a written license from Bionic.
 * BIONIC SPECIFICALLY DISCLAIMS ANY WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE AND DOCUMENTATION, IF ANY, PROVIDED HEREUNDER IS PROVIDED "AS IS". UNLESS AGREED IN A WRITTEN LICENSE AGREEMENT, BIONIC HAS NO OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS, OR MODIFICATIONS.
 * IN NO EVENT SHALL BIONIC BE LIABLE TO ANY PARTY FOR DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES, INCLUDING LOST PROFITS, ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF BIONIC HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.dv.akka

final class ImpressionData(
                            f1: String = "",
                            f2: String = "",
                            f3: String = "",
                            f4: String = "",
                            f5: String = "",
                            f6: String = "",
                            f7: String = "",
                            f8: String = "",
                            f9: String = "",
                            f10: String = "",
                            f11: String = "",
                            f12: String = "",
                            f13: String = "",
                            f14: String = "",
                            f15: String = "",
                            f16: String = "",
                            f17: String = "",
                            f18: String = "",
                            f19: String = "",
                            f20: String = "",
                            f21: String = "",
                            f22: String = "",
                            f23: String = "",
                            f24: String = "",
                            f25: String = "",
                            f26: String = "",
                            f27: String = "",
                            f28: String = "",
                            f29: String = "",
                            f30: String = "",
                            f31: String = "",
                            f32: String = "",
                            f33: String = "",
                            f34: String = "",
                            f35: String = "",
                            f36: String = "",
                            f37: String = "",
                            f38: String = "",
                            f39: String = "",
                            f40: String = "",
                            f41: String = "",
                            f42: String = "",
                            f43: String = "",
                            f44: String = "",
                            f45: String = "",
                            f46: String = "",
                            f47: String = "",
                            f48: String = "",
                            f49: String = "",
                            f50: String = "",
                            d1: Int = 0,
                            d2: Int = 0,
                            d3: Int = 0,
                            d4: Int = 0,
                            d5: Int = 0,
                            d6: Int = 0,
                            d7: Int = 0,
                            d8: Int = 0,
                            d9: Int = 0,
                            d10: Int = 0,
                            d11: Int = 0,
                            d12: Int = 0,
                            d13: Int = 0,
                            d14: Int = 0,
                            d15: Int = 0,
                            d16: Int = 0,
                            d17: Int = 0,
                            d18: Int = 0,
                            d19: Int = 0,
                            d20: Int = 0,
                            d21: Int = 0,
                            d22: Int = 0,
                            d23: Int = 0,
                            d24: Int = 0,
                            d25: Int = 0,
                            d26: Int = 0,
                            d27: Int = 0,
                            d28: Int = 0,
                            d29: Int = 0,
                            d30: Int = 0,
                            d31: Int = 0,
                            d32: Int = 0,
                            d33: Int = 0,
                            d34: Int = 0,
                            d35: Int = 0,
                            d36: Int = 0,
                            d37: Int = 0,
                            d38: Int = 0,
                            d39: Int = 0,
                            d40: Int = 0,
                            d41: Int = 0,
                            d42: Int = 0,
                            d43: Int = 0,
                            d44: Int = 0,
                            d45: Int = 0,
                            d46: Int = 0,
                            d47: Int = 0,
                            d48: Int = 0,
                            d49: Int = 0,
                            d50: Int = 0
                          )


case class ImpressionMessage(data:ImpressionData , var evtType:Int)