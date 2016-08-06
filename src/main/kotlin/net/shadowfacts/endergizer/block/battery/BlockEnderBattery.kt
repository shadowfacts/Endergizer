package net.shadowfacts.endergizer.block.battery

import net.minecraft.block.material.Material
import net.minecraft.block.properties.PropertyBool
import net.minecraft.block.properties.PropertyDirection
import net.minecraft.block.properties.PropertyEnum
import net.minecraft.block.state.BlockStateContainer
import net.minecraft.block.state.IBlockState
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.client.resources.I18n
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.item.EntityItem
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.EnumDyeColor
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.RayTraceResult
import net.minecraft.util.text.TextComponentTranslation
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.common.UsernameCache
import net.minecraftforge.oredict.OreDictionary
import net.shadowfacts.endergizer.MODID
import net.shadowfacts.endergizer.util.*
import net.shadowfacts.shadowmc.ShadowMC
import net.shadowfacts.shadowmc.block.BlockTE

/**
 * @author shadowfacts
 */
val INV: PropertyBool = PropertyBool.create("inv")
val ORIENTATION: PropertyDirection = PropertyDirection.create("orientation")
val COLOR1: PropertyEnum<EnumDyeColor> = PropertyEnum.create("color1", EnumDyeColor::class.java)
val COLOR2: PropertyEnum<EnumDyeColor> = PropertyEnum.create("color2", EnumDyeColor::class.java)

val VERTICAL_BOX = AxisAlignedBB(4/16.0, 0.0, 4/16.0, 12/16.0, 1.0, 12/16.0)
val NS_BOX = AxisAlignedBB(4/16.0, 4/16.0, 0.0, 12/16.0, 12/16.0, 1.0)
val EW_BOX = AxisAlignedBB(0.0, 4/16.0, 4/16.0, 1.0, 12/16.0, 12/16.0)

class BlockEnderBattery : BlockTE<TileEntityEnderBattery>(Material.ROCK, "enderBattery") {

	fun init(): BlockEnderBattery {
		defaultState = defaultState
				.withProperty(INV, false)
				.withProperty(ORIENTATION, EnumFacing.DOWN)
				.withProperty(COLOR1, EnumDyeColor.WHITE)
				.withProperty(COLOR2, EnumDyeColor.WHITE)
		blockHardness = 0.3f
		setCreativeTab(CreativeTabs.MISC)
		return this
	}

	override fun initItemModel() {
		ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(this), { stack ->
			var color1 = stack.getColor1()?.name
			var color2 = stack.getColor2()?.name
			if (color1 == null) color1 = EnumDyeColor.WHITE.name
			if (color2 == null) color1 = EnumDyeColor.WHITE.name
			ModelResourceLocation("$MODID:enderBattery", "color1=$color1,color2=$color2,inv=true,orientation=down")
		})
	}

	override fun getSubBlocks(item: Item, tab: CreativeTabs?, list: MutableList<ItemStack>) {
		EnumDyeColor.values().forEach {
			val stack = ItemStack(this)
			stack.setColors(it)
			list.add(stack)
		}
	}

	override fun getPickBlock(state: IBlockState, target: RayTraceResult, world: World, pos: BlockPos, player: EntityPlayer): ItemStack {
		val te = getTileEntity(world, pos)
		val stack = ItemStack(this)
		stack.setColor1(te.color1)
		stack.setColor2(te.color2)
		return stack
	}

	override fun addInformation(stack: ItemStack, player: EntityPlayer, tooltip: MutableList<String>, advanced: Boolean) {
		val color1 = stack.getColor1()
		val color2 = stack.getColor2()
		if (color1 != null && color2 != null) {
			tooltip.add(I18n.format("endergizer.color", I18n.format("shadowmc.dye." + color1.unlocalizedName), I18n.format("shadowmc.dye." + color2.unlocalizedName)))
		}
	}

	override fun breakBlock(world: World, pos: BlockPos, state: IBlockState) {
		val te = getTileEntity(world, pos)
		val stack = ItemStack(this)
		stack.setColor1(te.color1)
		stack.setColor2(te.color2)

		val entity = EntityItem(world, pos.x + 0.5, pos.y + 0.5, pos.z + 0.5, stack)
		world.spawnEntityInWorld(entity)

		super.breakBlock(world, pos, state)
	}

	override fun getDrops(world: IBlockAccess?, pos: BlockPos?, state: IBlockState?, fortune: Int): MutableList<ItemStack>? {
		return mutableListOf()
	}

	override fun getMetaFromState(state: IBlockState): Int {
		return state.getValue(ORIENTATION).index
	}

	@Deprecated("damn Mojang")
	override fun getStateFromMeta(meta: Int): IBlockState {
		return defaultState.withProperty(ORIENTATION, EnumFacing.getFront(meta))
	}

	@Deprecated("damn Mojang")
	override fun isOpaqueCube(state: IBlockState?): Boolean = false

	@Deprecated("damn Mojang")
	override fun isFullCube(state: IBlockState?): Boolean = false

	@Deprecated("damn Mojang")
	override fun getBoundingBox(state: IBlockState, world: IBlockAccess?, pos: BlockPos?): AxisAlignedBB {
		when (state.getValue(ORIENTATION)!!) {
			EnumFacing.UP, EnumFacing.DOWN -> return VERTICAL_BOX
			EnumFacing.NORTH, EnumFacing.SOUTH -> return NS_BOX
			EnumFacing.EAST, EnumFacing.WEST -> return EW_BOX
		}
	}

	override fun onBlockActivated(world: World, pos: BlockPos, state: IBlockState, player: EntityPlayer, hand: EnumHand, heldItem: ItemStack?, side: EnumFacing?, hitX: Float, hitY: Float, hitZ: Float): Boolean {
		val te = getTileEntity(world, pos)
		if (player.isSneaking) {
			val owner = UsernameCache.getLastKnownUsername(te.owner)
			if (owner != null) {
				ShadowMC.proxy.sendSpamlessMessage(player, TextComponentTranslation("enderBattery.owner", owner), 6498)
			}
			return true
		} else {
			if (heldItem != null) {
				val dyes = OreDictionary.getOreIDs(heldItem)
						.map { OreDictionary.getOreName(it) }
						.filter { it.startsWith("dye") && !it.equals("dye") }
						.map { it.substring(3).toUpperCase() }
						.map { if (it.equals("LIGHTBLUE")) EnumDyeColor.LIGHT_BLUE else EnumDyeColor.valueOf(it) }
				if (!dyes.isEmpty()) {
					val dye = dyes.first()
					var value: Float
					when (state.getValue(ORIENTATION)!!) {
						EnumFacing.UP -> value = Math.abs(1 - hitY)
						EnumFacing.DOWN -> value = hitY
						EnumFacing.NORTH -> value = Math.abs(1 - hitZ)
						EnumFacing.SOUTH -> value = hitZ
						EnumFacing.WEST -> value = Math.abs(1 - hitX)
						EnumFacing.EAST -> value = hitX
					}
					var changed = false
					if (value > 3/16.0 && value < 6/16.0) {
						te.color1 = dye
						changed = true
					} else if (value > 9/16.0 && value < 12/16.0) {
						te.color2 = dye
						changed = true
					}
					if (changed) {
						te.markDirty()
						world.markBlockRangeForRenderUpdate(pos, pos)
						heldItem.stackSize--
						if (heldItem.stackSize <= 0) {
							player.setHeldItem(hand, null)
						}
						player.swingArm(hand)
						return true
					}
				}
			}
		}
		return false
	}

	override fun onBlockPlaced(world: World, pos: BlockPos, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float, meta: Int, placer: EntityLivingBase?): IBlockState {
		return defaultState.withProperty(ORIENTATION, if (EnumFacing.Plane.HORIZONTAL.apply(facing)) facing else facing.opposite)
	}

	override fun onBlockPlacedBy(world: World, pos: BlockPos, state: IBlockState, placer: EntityLivingBase, stack: ItemStack) {
		val te = getTileEntity(world, pos)
		te.owner = placer.uniqueID
		te.color1 = stack.getColor1() ?: EnumDyeColor.WHITE
		te.color2 = stack.getColor2() ?: EnumDyeColor.WHITE
		te.markDirty()
		world.markBlockRangeForRenderUpdate(pos, pos)
	}

	override fun createBlockState(): BlockStateContainer = BlockStateContainer(this, INV, ORIENTATION, COLOR1, COLOR2)

	@Deprecated("damn Mojang")
	override fun getActualState(state: IBlockState, world: IBlockAccess, pos: BlockPos): IBlockState {
		val te = getTileEntity(world, pos)
		return state.withProperty(COLOR1, te.color1)
					.withProperty(COLOR2, te.color2)
	}

	override fun getTileEntityClass(): Class<TileEntityEnderBattery> = TileEntityEnderBattery::class.java

}